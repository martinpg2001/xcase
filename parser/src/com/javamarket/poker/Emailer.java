package com.javamarket.poker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.models.Recipient;
import com.microsoft.graph.serviceclient.GraphServiceClient;

public class Emailer {
    final String clientId = "YOUR_CLIENT_ID";
    final String tenantId = "YOUR_TENANT_ID";
    final String clientSecret = "YOUR_CLIENT_SECRET";

    ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
            .clientId(clientId)
            .clientSecret(clientSecret)
            .tenantId(tenantId)
            .build();

    ArrayList<String> scopes = new ArrayList<String>();
    scopes.add("https://graph.microsoft.com/.default");

    GraphServiceClient graphClient = new GraphServiceClient(clientSecretCredential, scopes);

    Message message = new Message();
    message.subject = "Sample Subject";
    ItemBody body = new ItemBody();
    body.contentType = BodyType.HTML;

    body.content = getReportHtml(); //getReportHtml method returns html string
    message.body = body;

    LinkedList<Recipient> toRecipientsList = new LinkedList<Recipient>();
    Recipient toRecipients = new Recipient();
    EmailAddress emailAddress = new EmailAddress();
    emailAddress.address = "abc@outlook.com";
    toRecipients.emailAddress = emailAddress;
    toRecipientsList.add(toRecipients);
    message.toRecipients = toRecipientsList;

    LinkedList<Recipient> ccRecipientsList = new LinkedList<Recipient>();
    Recipient ccRecipients = new Recipient();
    EmailAddress emailAddress1 = new EmailAddress();
    emailAddress1.address = "def@outlook.com";
    ccRecipients.emailAddress = emailAddress1;
    ccRecipientsList.add(ccRecipients);
    message.ccRecipients = ccRecipientsList;

    graphClient.users(fromEmailId)
            .sendMail(UserSendMailParameterSet
                    .newBuilder()
                    .withMessage(message)
                    .withSaveToSentItems(true)
                    .build())
            .buildRequest()
            .post();
    }
}
