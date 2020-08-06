package com.szells.membership.mail;

public interface IWelcomeTemplate {

    static String emailContent(String member_name) {
        member_name=member_name!=null?member_name:"";
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
                "        <title>Enrollment Mail</title>\n" +
                "        \n" +
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
                "                    <p style=\"margin-bottom: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">Hi <strong>" + member_name + "</strong></p>\n" +
                "                    <p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\"> Welcome to <strong>Szelss loyalty platform</strong> Loyalty platform. Congratulations on activating your member account with us. </p>\n" +
                "                    <p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">You are one step away from a world of offers! </p>\n" +
                "                    <p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">Please <a href=\"http://3.15.187.210/login\" style=\"    text-decoration: none;color: #17e0e2;font-weight: bold;\">login here</a> with your username and password to enroll in our customized packages created specifically for you.</p>\n" +
                "                    <p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">You could also copy the below link and paste in your browser address bar</p>\n" +
                "                   \n" +
                "                    <p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 50px !important;margin-top: 50px !important;\"><a href=\"http://3.15.187.210/login\" style=\"    text-decoration: none;color: #17e0e2;font-weight: bold;\">http://3.15.187.210/login</a></p>\n" +
                "                    <p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">Thanks,</p>\n" +
                "                    <p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">Syzegee Team</p>\n" +
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
        /*return "<!DOCTYPE html>\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                + "<head>\n"
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n"
                + "<!--[if !mso]><!-->\n"
                + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n"
                + "<!--<![endif]-->\n"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n"
                + "<title>Shoppie | Activation Completed</title>\n"
                + "<link rel=\"stylesheet\" href=\"http://szells.com.s3-website.us-east-2.amazonaws.com/styles/bootstrap.css\">\n"
                + "<link rel=\"stylesheet\" href=\"http://szells.com.s3-website.us-east-2.amazonaws.com/styles/web.css\">\n"
                + "<link rel=\"stylesheet\" href=\"http://szells.com.s3-website.us-east-2.amazonaws.com/styles/style.css\">\n"
                + "<link rel=\"stylesheet\" href=\"http://szells.com.s3-website.us-east-2.amazonaws.com/styles/responsive.css\">\n"
                + "<link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0///szells.com.s3-website.us-east-2.amazonaws.com/styles/font-awesome.min.css \">\n"
                + "<style type=\"text/css\">\n"
                + "</head>\n"
                + "\n"
                + "<body topmargin=\"0\" rightmargin=\"0\" bottommargin=\"0\" leftmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" width=\"100%\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; width: 100%; height: 100%; -webkit-font-smoothing: antialiased; text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; line-height: 100%;  background-color: #D8D8D8;color: #000000;\" bgcolor=\"#FFFFFF\" text=\"#000000\">\n"
                + "<table bgcolor=\"#ffffff\" width=\"700\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" className=\"devicewidth\">\n"
                + "<tr style=\"background: #20b3e5;\">\n"
                + "<td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 2.25%; padding-right: 6.25%; width: 87.5%; padding-top: 18px;padding-bottom: 23px;\">\n"
                + "<img border=\"0\" vspace=\"0\" hspace=\"0\" src=\"http://szells.com.s3-website.us-east-2.amazonaws.com/img/logo_11.png\" width=\"220\" height=\"auto\" alt=\"Logo\" title=\"Logo\" style=\"float: left; color: #000000; font-size: 10px; margin: 0; padding: 0; outline: none; text-decoration: none; -ms-interpolation-mode: bicubic; border: none; display: block;\"/>\n"
                + "<img border=\"0\" vspace=\"0\" hspace=\"0\" src=\"http://szells.com.s3-website.us-east-2.amazonaws.com/img/msg.png\" width=\"150\" height=\"auto\" alt=\"Logo\" title=\"Logo\" style=\"color: #000000;float: right;margin-bottom: -80px !important; font-size: 10px; margin: 0; padding: 0; outline: none; text-decoration: none; -ms-interpolation-mode: bicubic; border: none; display: block;\" className=\"position\"/>\n"
                + "</td>\n"
                + "</tr>\n"
                + "<tr>\n"
                + "<td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 5.25%; padding-right: 3.25%; width: 87.5%; font-size: 17px; font-weight: 400; line-height: 28px; padding-top:70px; padding-bottom:70px; color: #262626;font-family: Bw Glenn Sans; text-align:left\" className=\"paragraph\">\n"
                + "<p style=\"margin-bottom: 0 !important;font-size: 20px;margin-bottom: 11px !important; \">Hi " + member_name + "<strong>abhishek@abc.com</strong></p>\n"
                + "<p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\"> Welcome to <strong>Szells</strong> Loyalty platform. Congratulations on activating your member account with us. </p>\n"
                + "<p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">You are one step away from a world of offers! </p>\n"
                + "<p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">Please login here with your username and password to enroll in our customized packages created specifically for you.</p>\n"
                + "<p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">You could also copy the below link and paste in your browser address bar</p>\n"
                + "<p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 50px !important;margin-top: 50px !important;\"><a href=\"http://3.15.187.210/login\" style=\"text-decoration: none;color: #17e0e2;font-weight: bold;\">http://3.15.187.210/login</a></p>\n"
                + "<p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">Thanks,</p>\n"
                + "<p style=\"margin: 0 !important;font-size: 20px;margin-bottom: 11px !important;\">Syzegee Team</p>\n"
                + "</td>\n"
                + "</tr>\n"
                + "</table>\n"
                + "</body>\n"
                + "</html>\n";*/
    }
}
