/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webdevtool;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import java.util.regex.*;

/**
 *
 * @author qwer
 */
public class WebDevToolHelper {
    public static final Map<String, ArrayList<String>> parseHosts(String filename) {
        try {
            String s, host;
            Matcher matcher;
            int i, startFrom;
            Map<String, ArrayList<String>> hosts = new HashMap();
            Scanner hostsFile = new Scanner(new File(filename));
            Pattern mainPattern = Pattern.compile("([^ 	]+)[ |	]+([\\w. -]+)");
            Pattern domainsPattern = Pattern.compile("[^ 	]+");
            ArrayList<String> domains = new ArrayList<>();
            
            while (hostsFile.hasNext()) {
                s = hostsFile.nextLine().trim();
                if (s.length() == 0 || s.length() > 0 && s.charAt(0) == '#') continue;
                matcher = mainPattern.matcher(s);
                
                if (matcher.find()) {
                    host = matcher.group(1); // IP
                    matcher = domainsPattern.matcher(matcher.group(2)); // domains
                    
                    startFrom = 0;
                    domains.clear();
                    while (matcher.find(startFrom)) {
                        domains.add(matcher.group(0));
                        startFrom = matcher.end();
                    }
                    
                    if (hosts.get(host) != null) {
                        domains.addAll(hosts.get(host));
                        hosts.put(host, (ArrayList) domains.clone());
                    } else {
                        hosts.put(host, (ArrayList) domains.clone());
                    }
                    
                }
            }
            
            return hosts;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static final Map<String, String> parseVhosts(String path) {
        try {
            Map<String, String> vhosts = new HashMap<>();

            File vhostsFolder = new File(path);
            Scanner in;
            String s, content, serverName = "";
            int capturing = 0; // 0 - not vhost, 1 - capture in progress, 
            Matcher matcher;
            Pattern serverNamePattern = Pattern.compile("ServerName ([^ 	]+)");
            
            for (File vhostsFile : vhostsFolder.listFiles()) {
                in = new Scanner(vhostsFile);
                content = "";
                while (in.hasNext()) {
                    s = in.nextLine().trim();
                    if (s.length() > 0 && s.charAt(0) != '#') {
                        if (capturing == 1) {
                            content += s + "\n";

                            matcher = serverNamePattern.matcher(s);
                            
                            if (matcher.find()) {
                                serverName = matcher.group(1);
                            }
                            
                        } else {
                            if (s.contains("<VirtualHost")) {
                                content += s + "\n";
                                capturing = 1;
                            }
                        }
                        
                        if (s.contains("</VirtualHost>")) {
                            vhosts.put(serverName, new String(content));
                            content = "";
                            capturing = 0;
                        }
                        
                    }
                }
            }

            return vhosts;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WebDevToolHelper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
