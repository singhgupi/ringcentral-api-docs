package SummarizeConversation;

import java.io.IOException;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import com.ringcentral.*;
import com.ringcentral.definitions.*;

public class SummarizeConversation {
    static String NGROK_ADDRESS = "NGROK-TUNNEL-ADDRESS";
    static String WEBHOOK_URL = NGROK_ADDRESS + "/webhook";

    static RestClient restClient;

    public static void main(String[] args) {
      var obj = new SummarizeConversation();
      try {
        // Instantiate the SDK
        restClient = new RestClient("PRODUCTION-APP-CLIENT-ID", "PRODUCTION-APP-CLIENT-SECRET", "https://platform.ringcentral.com");

        // Authenticate a user using a personal JWT token
        restClient.authorize("PRODUCTION-JWT");

        obj.conversation_summary();
      } catch (RestException e) {
        System.out.println(e.getMessage());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    /*
    * Summarize the conversations
    */
    static private void conversation_summary() throws RestException, IOException {
      try {
        ArrayList<SummaryUnit> utterances = new ArrayList<>();
        String utterancesStr = "{'utterances':[{'end':8.96,'speakerId':'0','start':3.72,'text':'Good evening, thank you for calling electronics or this is Rachel. How may I assist you?'},{'end':14.280000000000001,'speakerId':'1','start':8.96,'text':'Hi, Rachel. I would like to know how to use this car. Bluetooth headset I recently purchased from your store.'},{'end':21.36,'speakerId':'0','start':14.280000000000001,'text':'Sure, ma\\'am, I can help you out with that, but before anything else, I have your name so that I can address you properly.'},{'end':23.58,'speakerId':'1','start':21.36,'text':'Yes, this is Meredith Blake.'},{'end':29.72,'speakerId':'0','start':23.9,'text':'Okay, thank you for that, Mrs. Plague, what exactly do you want done with your headset?'},{'end':31.939999999999998,'speakerId':'1','start':29.72,'text':'I want to know how to use it.'},{'end':39.08,'speakerId':'0','start':31.939999999999998,'text':'Okay, ma\\'am, I may get your headsets, modal name and number as well as that of your phone.'},{'end':45.18,'speakerId':'1','start':39.08,'text':'I bought a Plantronics Mark 2m165, and I use an iphone for S.'},{'end':51.4,'speakerId':'0','start':45.34,'text':'Okay, ma\\'am, so have you tried fairly pairing it with your headset with your phone?'},{'end':52.74,'speakerId':'1','start':51.4,'text':'Yes, I have.'},{'end':60.24,'speakerId':'0','start':53.22,'text':'Okay, first, I need you to unpair your device with your phone.'},{'end':61.42,'speakerId':'1','start':60.24,'text':'I am, okay?'},{'end':63.1,'speakerId':'0','start':61.62,'text':'Yes, ma\\'am.'},{'end':65.46,'speakerId':'1','start':63.42,'text':'OK, done.'},{'end':72.64,'speakerId':'0','start':65.74,'text':'Now, please switch off your phone, then turn it on again after around 5 s.'},{'end':74.34,'speakerId':'1','start':72.64,'text':'Switch it off and on.'},{'end':75.78,'speakerId':'0','start':74.46,'text':'Yes, please.'},{'end':77.78,'speakerId':'1','start':75.98,'text':'Done now what?'},{'end':80.74,'speakerId':'0','start':77.82,'text':'Okay, please parry it with your phone.'},{'end':84.26,'speakerId':'1','start':81.74,'text':'Okay, now what.'},{'end':88.19999999999999,'speakerId':'0','start':84.7,'text':'Ma\\'am, is your headset working now? That\\'s.'},{'end':95.12,'speakerId':'1','start':88.19999999999999,'text':'Just it I want to know how it works. I already told you that I have paired the device with my phone, but I am not sure if you get what I mean. I.'},{'end':101.7,'speakerId':'0','start':95.12,'text':'Well, yes, ma\\'am, I understand where you are coming from, that is why I am trying to help you, okay?'},{'end':107.7,'speakerId':'1','start':102.22,'text':'Well, we have already done that. I only ask a simple question. Why can\\'t you seem to get that?'},{'end':114.2,'speakerId':'0','start':108.14,'text':'Yes, ma\\'am, I understand where you are coming from. That is why I am trying to help.'},{'end':123.8,'speakerId':'1','start':114.2,'text':'You know what, no 1 I do not think you do. We have been at this for over 30 min going around in circles. I would like to speak to your supervisor. I obviously will not get any help from you.'}]}";
        JSONObject jsonObject = new JSONObject(utterancesStr);
        JSONArray utterancesArray = jsonObject.getJSONArray("utterances");
        for (int i = 0, size = utterancesArray.length(); i < size; i++) {
          JSONObject u = utterancesArray.getJSONObject(i);
          var unit = new SummaryUnit();
          unit.end = u.getDouble("end");
          unit.start = u.getDouble("start");
          unit.speakerId = u.getString("speakerId");
          unit.text = u.getString("text");
          utterances.add(unit);
        }
        var bodyParams = new SummaryInput();
        bodyParams.summaryType = "All";
        bodyParams.utterances =  utterances.toArray(new SummaryUnit[utterances.size()]);

        var callbackAddress = System.getenv("WEBHOOK_URL") + "/webhook";
        var queryParams = new CaiSummarizeParameters().webhook(callbackAddress);
        var resp = restClient.ai().text().v1().async().summarize().post(bodyParams, queryParams);
        System.out.println("Job ID: " + resp.jobId);
        System.out.println("Ready to receive response at: " + callbackAddress);
      } catch (RestException ex) {
        System.out.println("Unable to call the summarize API. " + ex.getMessage());
      }
    }
}