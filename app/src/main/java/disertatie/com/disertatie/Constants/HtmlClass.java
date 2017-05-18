package disertatie.com.disertatie.Constants;

/**
 * Created by Roxana on 5/18/2017.
 */

public class HtmlClass {
    public static String invoice = "<!doctype html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <title>Cerere de Oferta</title>\n" +
            "\n" +
            "    <style>\n" +
            "    .invoice-box{\n" +
            "        max-width:800px;\n" +
            "        margin:auto;\n" +
            "        padding:30px;\n" +
            "        border:1px solid #eee;\n" +
            "        box-shadow:0 0 10px rgba(0, 0, 0, .15);\n" +
            "        font-size:16px;\n" +
            "        line-height:24px;\n" +
            "        font-family:'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;\n" +
            "        color:#555;\n" +
            "    }\n" +
            "\n" +
            "    .invoice-box table{\n" +
            "        width:100%;\n" +
            "        line-height:inherit;\n" +
            "        text-align:left;\n" +
            "    }\n" +
            "\n" +
            "    .invoice-box table td{\n" +
            "        padding:5px;\n" +
            "        vertical-align:top;\n" +
            "    }\n" +
            "\n" +
            "    .invoice-box table tr td:nth-child(2){\n" +
            "        text-align:right;\n" +
            "    }\n" +
            "    .invoice-box table tr td:nth-child(3){\n" +
            "        text-align:right;\n" +
            "    }\n" +
            "    .invoice-box table tr td:nth-child(4){\n" +
            "        text-align:right;\n" +
            "    }\n" +
            "\n" +
            "    .invoice-box table tr.top table td{\n" +
            "        padding-bottom:20px;\n" +
            "        text-align:left;\n" +
            "    }\n" +
            "\n" +
            "    .invoice-box table tr.top table td:nth-child(2){\n" +
            "        padding-bottom:20px;\n" +
            "        text-align:right;\n" +
            "    }\n" +
            "\n" +
            "    .invoice-box table tr.top table td.title{\n" +
            "        font-size:35px;\n" +
            "        line-height:35px;\n" +
            "        color:#333;\n" +
            "        text-align: left\n" +
            "    }\n" +
            "\n" +
            "    .invoice-box table tr.information table td{\n" +
            "\n" +
            "    }\n" +
            "    .invoice-box table tr.information table td:nth-child(2){\n" +
            "        padding-bottom:40px;\n" +
            "        text-align:right;\n" +
            "    }\n" +
            "\n" +
            "    .invoice-box table tr.heading td{\n" +
            "        background:#eee;\n" +
            "        border-bottom:1px solid #ddd;\n" +
            "        font-weight:bold;\n" +
            "        width: 309px;\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "    .invoice-box table tr.details td{\n" +
            "        padding-bottom:20px;\n" +
            "    }\n" +
            "\n" +
            "    .invoice-box table tr.item td{\n" +
            "        border-bottom:1px solid #eee;\n" +
            "    }\n" +
            "\n" +
            "    .invoice-box table tr.item.last td{\n" +
            "        border-bottom:1px solid #eee;\n" +
            "    }\n" +
            "\n" +
            "    .invoice-box table tr.total td:nth-child(4){\n" +
            "        border-top:2px solid #eee;\n" +
            "        font-weight:bold;\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "        .invoice-box table tr.information table td{\n" +
            "            width:100%;\n" +
            "            display:block;\n" +
            "            text-align:left;\n" +
            "        }\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    </style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<div class=\"invoice-box\">\n" +
            "    <table cellpadding=\"0\" cellspacing=\"0\">\n" +
            "        <tr class=\"top\">\n" +
            "            <td colspan=\"4\">\n" +
            "                <table>\n" +
            "                    <tr>\n" +
            "\n" +
            "                        <td class=\"title\">\n" +
            "                            Cerere de Oferta\n" +
            "                        </td>\n" +
            "\n" +
            "                        <td>\n" +
            "                            Numar Document #: 123<br>\n" +
            "                            Data: January 1, 2015<br>\n" +
            "                            Termen Raspuns: February 1, 2015\n" +
            "                        </td>\n" +
            "\n" +
            "                    </tr>\n" +
            "                </table>\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "        </table>\n" +
            "\n" +
            "        <tr class=\"information\">\n" +
            "            <table cellpadding=\"0\" cellspacing=\"0\">\n" +
            "            <td colspan=\"4\">\n" +
            "\n" +
            "                <table>\n" +
            "                    <tr>\n" +
            "\n" +
            "                        <td>\n" +
            "                            Next Step Webs, Inc.<br>\n" +
            "                            12345 Sunny Road<br>\n" +
            "                            Sunnyville, TX 12345\n" +
            "                        </td>\n" +
            "\n" +
            "                        <td>\n" +
            "                            Acme Corp.<br>\n" +
            "                            John Doe<br>\n" +
            "                            john@example.com\n" +
            "                        </td>\n" +
            "                    </tr>\n" +
            "                </table>\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "\n" +
            "\n" +
            "\n" +
            "        <tr class=\"heading\">\n" +
            "            <td>\n" +
            "                Material\n" +
            "            </td>\n" +
            "\n" +
            "            <td>\n" +
            "                Cantitate\n" +
            "            </td>\n" +
            "\n" +
            "            <td>\n" +
            "                Pret\n" +
            "            </td>\n" +
            "            <td>\n" +
            "                Data Livrare\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "\n" +
            "        <tr class=\"item\">\n" +
            "            <td>\n" +
            "                Coca-Cola\n" +
            "            </td>\n" +
            "\n" +
            "            <td>\n" +
            "                10.00\n" +
            "            </td>\n" +
            "\n" +
            "            <td>\n" +
            "                $300.00\n" +
            "            </td>\n" +
            "\n" +
            "            <td>\n" +
            "                01.05.2017\n" +
            "            </td>\n" +
            "\n" +
            "        </tr>\n" +
            "\n" +
            "        <tr>\n" +
            "            <td contenteditable='true'>\n" +
            "                ...\n" +
            "            </td>\n" +
            "\n" +
            "            <td contenteditable='true'>\n" +
            "                ...\n" +
            "            </td>\n" +
            "\n" +
            "            <td contenteditable='true'>\n" +
            "                ...\n" +
            "            </td>\n" +
            "\n" +
            "            <td contenteditable='true'>\n" +
            "                ...\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "\n" +
            "\n" +
            "        <tr class=\"total\">\n" +
            "            <td></td>\n" +
            "            <td></td>\n" +
            "            <td></td>\n" +
            "            <td>\n" +
            "                Total: $385.00\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "                <tr>\n" +
            "                    <td></td>\n" +
            "                    <td></td>\n" +
            "                    <td></td>\n" +
            "                    <td>\n" +
            "                        <input type=\"checkbox\" name=\"confirma\" value=\"confirma\"> Confirma<br>\n" +
            "                    </td>\n" +
            "\n" +
            "                </tr>\n" +
            "                <tr>\n" +
            "                    <td></td>\n" +
            "                    <td></td>\n" +
            "                    <td></td>\n" +
            "                    <td>\n" +
            "                        <button onclick=\"foo()\">Trimite</button>\n" +
            "                    </td>\n" +
            "                </tr>\n" +
            "    </table>\n" +
            "            <form class=\"form_checkbox\">\n" +
            "\n" +
            "            </form>\n" +
            "\n" +
            "</div>\n" +
            "<script>\n" +
            "function myFunction() {\n" +
            "    document.getElementById(\"demo\").innerHTML = \"Hello World\";\n" +
            "}\n" +
            "function foo(){\n" +
            "document.getElementById(\"demo\").innerHTML = \"Start\";\n" +
            "var request = new XMLHttpRequest();\n" +
            "request.open('POST', \"https://fcm.googleapis.com/fcm/send\", true);\n" +
            "request.setRequestHeader('Content-Type', 'application/json');\n" +
            "request.setRequestHeader('Authorization', 'key=AAAAVNU4yx8:APA91bFPqjAAqw9GEEb_RAnDsujxTR-sE-cQ8zxFQAU1t13Z3XNrR8NwK8gIBoSreVVte5nShz13qW21pt4PqCh__YZmG64Y9kE0iRWoc7aFr9eaW6IFlKoR4UVup2nOvPba7NCJXXGH');\n" +
            "\n" +
            "document.getElementById(\"demo\").innerHTML = \"Start 2\";\n" +
            "request.onreadystatechange = function () {\n" +
            "    if (request.readyState === 4) {\n" +
            "       alert(request.responseText);\n" +
            "    }\n" +
            "    document.getElementById(\"demo\").innerHTML = \"Callback finished\";\n" +
            "}\n" +
            "request.send('{'+\n" +
            "  '\"to\" : \"eFGq6m0IBpw:APA91bHTsc6eARAzFJ1jILaGJybiN0ifp--koTj3MaL1KaJJhCgkY3pcVtGCbywe5ctPwmx7VrO41YavcLzW0kPk04pVdO6GtCTnlzSxf785q6v94QDo6DS3PrWyh6IgwEnTxSa94Cj3\",'+\n" +
            "  '\"notification\" : { \"title\" : \"From HTML\" , \"body\" : \"123456\" }'+\n" +
            "'}');\n" +
            "\n" +
            "document.getElementById(\"demo\").innerHTML = \"Attempting send\";\n" +
            "}\n" +
            "</script>\n" +
            "</body>\n" +
            "</html>";
}
