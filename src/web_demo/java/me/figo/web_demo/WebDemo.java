package me.figo.web_demo;

import static spark.Spark.*;
import me.figo.FigoConnection;
import me.figo.FigoSession;
import me.figo.internal.TokenResponse;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;


public class WebDemo {
    protected static FigoConnection connection = new FigoConnection("CaESKmC8MAhNpDe5rvmWnSkRE_7pkkVIIgMwclgzGcQY", "STdzfv0GXtEj_bwYn7AgCVszN1kKq5BdgEIKOM_fzybQ", "http://localhost:3000/callback");
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void main(String[] args) {
        setPort(3000);
        
        get("/logout", (req, res) -> {
            req.session(true).invalidate();
            res.redirect("/");
            return null;
        });
        
        get("/callback", (req, res) -> {
            if (req.queryParams("state").compareTo("qweqwe") != 0) {
                throw new RuntimeException("invalid redirect");
            }
            
            try {
                TokenResponse token_dict = connection.convertAuthenticationCode(req.queryParams("code"));
                req.session(true).attribute("figo_token", token_dict.access_token);
            } catch (Exception e) {
            }
            
            res.redirect("/");
            return null;
        });
        
        get("/:accountId", (req, res) -> {
            if (req.session(true).attribute("figo_token") == null) {
                res.redirect(connection.getLoginUrl("accounts=ro transactions=ro balance=ro user=ro", "qweqwe"));
                return null;
            }
            
            FigoSession session = new FigoSession(req.session().attribute("figo_token"));
            
            Map map = new HashMap();
            try {
                map.put("user", session.getUser());
                map.put("accounts", session.getAccounts());
                map.put("current_account", session.getAccount(req.params(":accountId")));
                map.put("transactions", session.getTransactions(req.params(":accountId")));
            } catch (Exception e) {
            }
            
            return new ModelAndView(map, "me/figo/web_demo/index.wm"); 
        }, new VelocityTemplateEngine());
        
        get("/", (req, res) -> {
            if (req.session(true).attribute("figo_token") == null) {
                res.redirect(connection.getLoginUrl("accounts=ro transactions=ro balance=ro user=ro", "qweqwe"));
                return null;
            }
            
            FigoSession session = new FigoSession(req.session().attribute("figo_token"));
            
            Map map = new HashMap();
            try {
                map.put("user", session.getUser());
                map.put("accounts", session.getAccounts());
                map.put("transactions", session.getTransactions());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return new ModelAndView(map, "me/figo/web_demo/index.wm");
        }, new VelocityTemplateEngine());
     }
}
