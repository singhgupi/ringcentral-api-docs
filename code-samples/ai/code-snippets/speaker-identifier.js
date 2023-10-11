var platform = require('./../quick-start.js').platform;
let NGROK = "http://c1eb-69-181-201-33.ngrok-free.app"
let WEBHOOK_URL = NGROK + "/webhook";
let CONTENT_URI = 'https://rclabs-addin-resources.s3.us-east-1.amazonaws.com/media/Phong-Shashi.mp3'
speaker_identification()



// Next line must be at the 10th line!
platform.on(platform.events.loginSuccess, () => {
    speaker_identification()
})

platform.on(platform.events.loginError, function(e){
    console.log("Unable to authenticate to platform. Check credentials.", e.message)
    process.exit(1)
});

NGROK = "http://c1eb-69-181-201-33.ngrok-free.app"
WEBHOOK_URL = NGROK + "/webhook";

/*
* Identify speakers specified by the enrollment id provided in the enrollmentIds array
*/
async function speaker_identification() {
  try{
    let bodyParams = {
          contentUri:                   CONTENT_URI,
          encoding:                     "Mpeg",
          languageCode:                 "en-US",
          source:                       "RingCentral",
          audioType:                    "CallCenter",
          enableVoiceActivityDetection: true,
          enrollmentIds: [ '1465518021', '1426275020' ] // [ '1426275020', '3220092020' ]
      }
    let endpoint = `/ai/audio/v1/async/speaker-identify?webhook=${WEBHOOK_URL}`
    var resp = await platform.post(endpoint, bodyParams)

    var jsonObj = await resp.json()
    console.log("Speaker identification job:", jsonObj)
  }catch (e){
    console.log("Unable to call speaker identification.", e.message)
  }
}
