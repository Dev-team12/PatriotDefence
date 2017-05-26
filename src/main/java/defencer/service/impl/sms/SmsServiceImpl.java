package defencer.service.impl.sms;

import defencer.model.Instructor;
import defencer.model.Project;
import defencer.service.SmsService;

import java.io.*;
import java.net.*;
import java.util.Objects;

/**
 * API sms service.
 *
 * @author Igor Hnes on 21.05.17.
 */
public class SmsServiceImpl implements SmsService {

    private static final String SMS_LOGIN = "Project PD";
    private static final String SMS_PASSWORD = "VorohamSmert";
    private static final String SMS_CHARSET = "utf-8";

    /**
     * {@inheritDoc}.
     */
    @Override
    public String getBalance() {

        String[] m;
        m = smsSendCmd("balance", "");

        return m.length == 2 ? "" : m[0];
    }

    /**
     * Send message to destination.
     */
    private String[] smsSendCmd(String cmd, String arg) {

        String ret = ",";
        try {
            String url = "http" + "://smsc.ru/sys/" + cmd + ".php?login=" + URLEncoder.encode(SMS_LOGIN, SMS_CHARSET) + "&psw=" + URLEncoder.encode(SMS_PASSWORD, SMS_CHARSET) + "&fmt=1&charset=" + SMS_CHARSET + "&" + arg;

            int i = 0;
            final int timeWaiting = 2000;
            final int smthUntilThree = 3;
            do {
                if (i > 0) {
                    Thread.sleep(timeWaiting);
                }
                ret = smsReadUrl(url);
            }
            while (Objects.equals(ret, "") && ++i < smthUntilThree);
        } catch (UnsupportedEncodingException | InterruptedException ignored) {
            // NON
        }

        return ret.split(",");
    }

    /**
     * Read url and send message.
     */
    private String smsReadUrl(String url) {

        StringBuilder line = new StringBuilder();
        String realUrl = url;
        String[] param = {};
        final int length = 2000;
        boolean isPost = url.length() > length;

        if (isPost) {
            param = url.split("\\?", 2);
            realUrl = param[0];
        }

        try {
            URL u = new URL(realUrl);
            InputStream is;

            if (isPost) {
                URLConnection conn = u.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream(), SMS_CHARSET);
                os.write(param[1]);
                os.flush();
                os.close();
                is = conn.getInputStream();
            } else {
                is = u.openStream();
            }

            InputStreamReader reader = new InputStreamReader(is, SMS_CHARSET);
            int ch;
            while ((ch = reader.read()) != -1) {
                line.append((char) ch);
            }
            reader.close();
        } catch (IOException ignored) {
            // NON
        }
        return line.toString();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void send(String phone, Instructor instructor, Project project) {

        final String message = buildMessage(instructor, project);
        try {
            smsSendCmd("send", "cost=3&phones=" + URLEncoder.encode(phone, SMS_CHARSET) + "&mes=" + URLEncoder
                    .encode(message, SMS_CHARSET) + "&translit=" + 1 + "&id=" + "" + "" + (Objects.equals("", "") ? "" : "&sender=" + URLEncoder
                    .encode("", SMS_CHARSET)) + "" + (Objects.equals("", "") ? "" : "&" + ""));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Build message before send.
     */
    private String buildMessage(Instructor instructor, Project project) {
        return "Dear "
                + instructor.getFirstName()
                + " "
                + instructor.getLastName()
                + " you were invited on course from Patriot Defence."
                + "\n"
                + "Project: "
                + project.getNameId()
                + "\n"
                + "Start Date: "
                + project.getDateStart()
                + " Finish Date: "
                + project.getDateFinish()
                + "\n"
                + "Place: "
                + project.getPlace()
                + "\n"
                + "Description: "
                + project.getDescription()
                + "\n"
                + "List of instructors: "
                + "\n"
                + project.getInstructors()
                + "\n"
                + "Author of project "
                + project.getAuthor()
                + "\n"
                + "Please confirm participation."
                + "\n"
                + "Have a nice day -)"
                + "\n"
                + "Your Patriot Defence!!!";
    }
}
