package com.szells.membership.mail;

/**
 * @author Riya Patel
 */
public interface IActivationTemplate {
    static String url="http://ec2-18-219-247-17.us-east-2.compute.amazonaws.com:9080/?crafterSite=member-templates";
    static String emailContent(String emailId,String activationCode) {
        return "\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "\n" +
                "    <head>\n" +
                "        <meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"/>\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                "        <meta name=\"format-detection\" content=\"telephone=no\" />\n" +
                "\n" +
                "\n" +
                "\n" +
                "        <title>Activation Mail</title>\n" +
                "        <style>\n" +
                "/*            .position{\n" +
                "                position: absolute;right: 29%;\n" +
                "            }\n" +
                "            @media (max-width:768px){\n" +
                "                .position{\n" +
                "                    position: absolute;\n" +
                "                    right: -80% !important;\n" +
                "                } \n" +
                "            }*/\n" +
                "        </style>\n" +
                "    </head>\n" +
                "\n" +
                "\n" +
                "    <body topmargin=\"0\" rightmargin=\"0\" bottommargin=\"0\" leftmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" width=\"100%\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; width: 100%; height: 100%; -webkit-font-smoothing: antialiased; text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; line-height: 100%;\n" +
                "          background-color: #D8D8D8;\n" +
                "          color: #000000;\" bgcolor=\"#FFFFFF\" text=\"#000000\">\n" +
                "        <table bgcolor=\"#ffffff\" width=\"700\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" className=\"devicewidth\" style=\"\">\n" +
                "            <tr style=\"background: #20b3e5;\">\n" +
                "                <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 2.25%; padding-right: 6.25%; width: 87.5%;\n" +
                "                    padding-top: 18px;padding-bottom: 23px;\">\n" +
                "                    <img border=\"0\" vspace=\"0\" hspace=\"0\" src=\"http://ilucenttechnolabs.com/projects/shoppi/assets/img/logo_11.png\" width=\"220\"\n" +
                "                         height=\"auto\" alt=\"Logo\" title=\"Logo\"\n" +
                "                         style=\"    float: left;\n" +
                "                         color: #000000;\n" +
                "                         font-size: 10px; margin: 0; padding: 0; outline: none; text-decoration: none; -ms-interpolation-mode: bicubic; border: none; display: block;\"/>\n" +
                "                    <img border=\"0\" vspace=\"0\" hspace=\"0\" src=\"http://ilucenttechnolabs.com/projects/shoppi/assets/img/msg.png\" width=\"150\"\n" +
                "                         height=\"auto\" alt=\"Logo\" title=\"Logo\"\n" +
                "                         style=\" \n" +
                "                         color: #000000;float: right;margin-bottom: -80px !important;\n" +
                "\n" +
                "                         font-size: 10px; margin: 0; padding: 0; outline: none; text-decoration: none; -ms-interpolation-mode: bicubic; border: none; display: block;\" className=\"position\"/>\n" +
                "                </td>\n" +
                "\n" +
                "\n" +
                "            </tr>\n" +
                "            <tr style=\"\">\n" +
                "                <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 5.25%; padding-right: 3.25%; width: 87.5%; font-size: 17px; font-weight: 400; line-height: 28px;;\n" +
                "                    padding-top:70px; padding-bottom:70px;\n" +
                "                    color: #262626;\n" +
                "                    font-family: Bw Glenn Sans; text-align:left\" className=\"paragraph\">\n" +
                "                    <p style=\"margin-bottom: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">Hi <strong>"+emailId+"</strong></p>\n" +
                "                    <p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\"> We've received your request for a single-use-code to active your Szells account. </p>\n" +
                "                    <p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">Your single user code is : <strong style=\"    font-size: 30px;\">"+activationCode+"</strong></p>\n" +
                "                    <p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">and</p>\n" +
                "                    <p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">Click here for complete your <a href="+url+" style=\"    text-decoration: none;color: #17e0e2;font-weight: bold;\">Signup </a> process</p>\n" +
                "                    <p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">or</p>\n" +
                "                    <p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">Copy the below link and paste your browser address bar</p>\n" +
                "                    <p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 50px !important;\"><a href="+url+"  style=\"    text-decoration: none;color: #17e0e2;font-weight: bold;\">"+url+"</a></p>\n" +
                "                    <p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">Thanks,</p>\n" +
                "                    <p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">Szells Team</p>\n" +
                "\n" +
                "\n" +
                "                </td>\n" +
                "            </tr> \n" +
                "\n" +
                "\n" +
                "        </table>\n" +
                "    </body>\n" +
                "\n" +
                "</html>";
    }

}