# Notification Apps

A RingCentral notification app is a specialized type of an app intended to help developers deliver messages to the teams into which they are installed, in response to events and triggers that happen outside of RingCentral.

A simple example of a notication app that most developers can quickly grok is the Github app that posts a message to team whenever a pull request is submitted to a github respository.

## Notification app features

1. Notification apps are promoted directly within RingCentral's desktop client to help customers find and install them. 
2. Notification apps optionally post interactive message elements allowing users to take action directly on a message without ever having to leave the RingCentral app. 
3. Notification apps can optionally provide users with the means to install the app from within the RingCentral desktop client via an embedded iframe.

## Creating a notification app

Creating a notification app is identical in most respects to [creating an app](../../../basics/create-app/) of any other type. Notification Apps do however have a few additional fields that need to be populated by the developer in order to deliver their functionality as designed and intended. 

### Display preferences

An app's display name and app icon are required fields for a notification app. The values provided here will help attribute the messages posted by your app.

<img src="../display-prefs.png" class="img-fluid" style="width:30%">

### Interactive messages

### Integrated installation

It is highly recommend developers enable the "integrated installation in RingCentral app" feature to help users both discover and install the app you build. 

<img src="../config-prefs.png" class="img-fluid" style="width:30%">

Preferences include:

* **Manual installation instructions** - this text will appear when a user elects to read the "manual installation instructions" associated with your app.
* **An installation URL** - if your app supports the customer's ability to setup/configure your app via the web, enter the URL to that flow in this field. See "Automating App Setup" below.

#### {{ WEBHOOK_URL }} tokens

Setting up a notification app requires a customer to register a special URL generated by the RingCentral platform with a third-party service. That service will then deliver messages to that URL for processing. This URL will be inserted into your manual installation instructions by replacing the string `{{ WEBHOOK_URL }}` with a UI element that will make it easy for users to copy the URL to their clipboard.

Depending upon the service being integration with, you may not want the webhook URL rendered with a form element and a copy-to-clipboard button. To support other use cases, we support the following additional syntaxes.

| Syntax | Output |
|-|-|
| `{{ WEBHOOK_URL }}` | The URL adorned with HTML to render a copy-to-clipboard button. |
| `{{ WEBHOOK_URL raw }}` | The original URL in plain text. |
| `{{ WEBHOOK_URL id_only }}` | The webhook ID, e.g. "3053f6cf-b6de-418c-xxxx-2eb222cdab4e" |
| `{{ WEBHOOK_URL base}}` | The base URL of the webhook, e.g. "https://hooks.glip.com/webhook/v2" |
