package disertatie.com.disertatie.Utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import disertatie.com.disertatie.Constants.Constants;
import disertatie.com.disertatie.entities.Furnizor;
import disertatie.com.disertatie.entities.Material;

/**
 * Created by roxana on 18.05.2017.
 */

public class GeneratorCerereDeOferta {
    private static final String styleHtml = "    <style>\n" +
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
            "    </style>\n";
    private static final String TAG = "Logistica";

    public static String generate(int codDocument, String termenRaspuns, Furnizor furnizor, Material material,
                                  double cantitatePropusaMaterial, double pretMaterial,
                                  String termenLivrare, String firebaseCloudMessagingId)
    {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateformat.format(new Date());
       // String termenRaspunsString = dateformat.format(termenRaspuns);

        String adresaFurnizorString = furnizor.getAdresa().toStringCustom();
        Log.d(TAG,adresaFurnizorString.toString());

       // String termenLivrareString = dateformat.format(termenLivrare);

        String invoice = "<!doctype html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>Cerere de Oferta</title>\n" +
                "\n" +
                styleHtml +
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
                "                           <div <span>Numar Document #: </span><span  id='divCodDocument'>"+codDocument+"</span></div>\n" +
                "                           <div>Data: "+currentDate+"</div>\n" +
                "                           <div>Termen Raspuns: "+termenRaspuns+"</div>\n" +
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
                                            adresaFurnizorString.replace("\n","<br>") +
                "                        </td>\n" +
                "\n" +
                "                        <td>\n" +
                                                furnizor.getDenumire_furnizor()+"<br>\n" +
                                                furnizor.getEmail()+ "<br>\n" +
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
                                material.getDenumire_material() + "\n" +
                "            </td>\n" +
                "\n" +
                "            <td>\n" +
                                cantitatePropusaMaterial + "\n" +
                "            </td>\n" +
                "\n" +
                "            <td>\n" +
                                pretMaterial+ "\n" +
                "            </td>\n" +
                "\n" +
                "            <td>\n" +
                                termenLivrare + "\n" +
                "            </td>\n" +
                "\n" +
                "        </tr>\n" +
                "\n" +
                "        <tr>\n" +
                "            <td>\n" +
                "            </td>\n" +
                "\n" +
                "            <td id='tdCantitate' contenteditable='true'>...</td>\n" +
                "            <td id='tdPret' contenteditable='true'>...</td>\n" +
                "            <td id='tdData' contenteditable='true'>...</td>\n" +
                "        </tr>\n" +
                "\n" +
                "\n" +
                "        <tr class=\"total\">\n" +
                "            <td></td>\n" +
                "            <td></td>\n" +
                "            <td></td>\n" +
                "            <td>\n" +
                                "Total: "+cantitatePropusaMaterial*pretMaterial + "\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "                <tr>\n" +
                "                    <td></td>\n" +
                "                    <td></td>\n" +
                "                    <td></td>\n" +
                "                    <td><button onclick=\"sendChange()\">Actualizare</button></td>\n" +
                "\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td></td>\n" +
                "                    <td></td>\n" +
                "                    <td></td>\n" +
                "                    <td>\n" +
                "                        <button onclick=\"sendAccept()\">Acceptare</button>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "    </table>\n" +
                "            <form class=\"form_checkbox\">\n" +
                "\n" +
                "            </form>\n" +
                "\n" +
                "</div>\n" +
                "<script>\n" +
                "function sendAccept(){\n" +
                "var request = new XMLHttpRequest();\n" +
                "request.open('POST', 'https://fcm.googleapis.com/fcm/send', true);\n" +
                "request.setRequestHeader('Content-Type', 'application/json');\n" +
                "request.setRequestHeader('Authorization', 'key=AAAAVNU4yx8:APA91bFPqjAAqw9GEEb_RAnDsujxTR-sE-cQ8zxFQAU1t13Z3XNrR8NwK8gIBoSreVVte5nShz13qW21pt4PqCh__YZmG64Y9kE0iRWoc7aFr9eaW6IFlKoR4UVup2nOvPba7NCJXXGH');\n" +
                "\n" +
                "var sCodDocument = document.getElementById('divCodDocument').textContent;\n" +
                "var sCantitate = document.getElementById('tdCantitate').textContent;\n" +
                "var sPret = document.getElementById('tdPret').textContent;\n" +
                "var sDataLivrare = document.getElementById('tdData').textContent;\n" +
                "var j = '{'+\n" +
                "  '\"to\" : \""+firebaseCloudMessagingId+"\",'+\n" +
                //"  '\"notification\" : { \"title\" : \""+furnizor.getDenumire_furnizor()+" a acceptat oferta\"},'+\n" +
                "  '\"data\" : {'+\n"+
                "     '\"codDocument\" : \"' + sCodDocument + '\",'+\n"+
                "     '\"cantitate\" : \"' + sCantitate + '\",'+\n"+
                "     '\"pret\" : \"' + sPret + '\",'+\n"+
                "     '\"status\" : \""+ Constants.ACCEPTAT + "\",'+\n"+
                "     '\"dataLivrare\" : \"' + sDataLivrare + '\"'+\n"+
                "  '}'+\n" +
                "'}';\n"+
                "request.onreadystatechange = function () {\n" +
                "    if (request.readyState === 4) {\n" +
                "       alert('Oferta a fost trimisa cu succes');\n" +
                "    }\n" +
                "}\n" +
                "request.send(j);\n" +
                "}\n" +
                "function sendChange(){\n" +
                "var request = new XMLHttpRequest();\n" +
                "request.open('POST', 'https://fcm.googleapis.com/fcm/send', true);\n" +
                "request.setRequestHeader('Content-Type', 'application/json');\n" +
                "request.setRequestHeader('Authorization', 'key=AAAAVNU4yx8:APA91bFPqjAAqw9GEEb_RAnDsujxTR-sE-cQ8zxFQAU1t13Z3XNrR8NwK8gIBoSreVVte5nShz13qW21pt4PqCh__YZmG64Y9kE0iRWoc7aFr9eaW6IFlKoR4UVup2nOvPba7NCJXXGH');\n" +
                "\n" +
                "var sCodDocument = document.getElementById('divCodDocument').textContent;\n" +
                "var sCantitate = document.getElementById('tdCantitate').textContent;\n" +
                "var sPret = document.getElementById('tdPret').textContent;\n" +
                "var sDataLivrare = document.getElementById('tdData').textContent;\n" +
                "var j = '{'+\n" +
                "  '\"to\" : \""+firebaseCloudMessagingId+"\",'+\n" +
                //"  '\"notification\" : { \"title\" : \""+furnizor.getDenumire_furnizor()+" a schimbat oferta\"},'+\n" +
                "  '\"data\" : {'+\n"+
                "     '\"codDocument\" : \"' + sCodDocument + '\",'+\n"+
                "     '\"cantitate\" : \"' + sCantitate + '\",'+\n"+
                "     '\"pret\" : \"' + sPret + '\",'+\n"+
                "     '\"status\" : \""+ Constants.MODIFICAT + "\",'+\n"+
                "     '\"dataLivrare\" : \"' + sDataLivrare + '\"'+\n"+
                "  '}'+\n" +
                "'}';\n"+
                "request.onreadystatechange = function () {\n" +
                "    if (request.readyState === 4) {\n" +
                "       alert('Actualizarea a fost trimisa cu succes');\n" +
                "    }\n" +
                "}\n" +
                "request.send(j);\n" +
                "}\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>";
        return invoice;
    }
}
