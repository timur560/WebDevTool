/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webdevtool;

/**
 *
 * @author qwer
 */
public class WebDevTool {

    public static final String HOSTS_PATH = "/etc/hosts";
    public static final String VHOSTS_AVAILABLE_PATH = "/etc/apache2/sites-available";
    public static final String VHOSTS_ENABLED_PATH = "/etc/apache2/sites-enabled";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }
    
}
