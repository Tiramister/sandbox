package org.example;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.ContentBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ConversationRole;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseResponse;
import software.amazon.awssdk.services.bedrockruntime.model.Message;

public class Main {
  public static void main(String[] args) {
    try (var client =
        BedrockRuntimeClient.builder()
            .credentialsProvider(DefaultCredentialsProvider.create())
            .region(Region.US_WEST_2)
            .build(); ) {
      var model = "anthropic.claude-3-5-sonnet-20241022-v2:0";

      var text = "Clojure で \"hello world\" プログラムを書いてください。";
      var message =
          Message.builder()
              .content(ContentBlock.fromText(text))
              .role(ConversationRole.USER)
              .build();

      ConverseResponse response =
          client.converse(
              request ->
                  request
                      .modelId(model)
                      .messages(message)
                      .inferenceConfig(
                          config -> config.maxTokens(512).temperature(0.5F).topP(0.9F)));

      // JSON と同じ構造
      String responseText = response.output().message().content().getFirst().text();
      System.out.println(responseText);
    }
  }
}
