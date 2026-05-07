package dev.langchain4j.model.anthropic;

import static dev.langchain4j.model.chat.Capability.RESPONSE_FORMAT_JSON_SCHEMA;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AnthropicStreamingChatModelCapabilitiesTest {

    @ParameterizedTest
    @ValueSource(
            strings = {
                "claude-opus-4-7",
                "claude-opus-4-6",
                "claude-sonnet-4-6",
                "claude-opus-4-5-20251101",
                "claude-sonnet-4-5-20250929",
                "claude-haiku-4-5-20251001"
            })
    void supportedCapabilities_includesJsonSchema_forStructuredOutputModels(String modelName) {
        AnthropicStreamingChatModel model = AnthropicStreamingChatModel.builder()
                .apiKey("dummy-key")
                .modelName(modelName)
                .build();

        assertThat(model.supportedCapabilities()).contains(RESPONSE_FORMAT_JSON_SCHEMA);
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                "claude-opus-4-1-20250805",
                "claude-opus-4-20250514",
                "claude-sonnet-4-20250514",
                "claude-3-5-sonnet-20240620",
                "some-future-model"
            })
    void supportedCapabilities_excludesJsonSchema_forOlderOrUnknownModels(String modelName) {
        AnthropicStreamingChatModel model = AnthropicStreamingChatModel.builder()
                .apiKey("dummy-key")
                .modelName(modelName)
                .build();

        assertThat(model.supportedCapabilities()).doesNotContain(RESPONSE_FORMAT_JSON_SCHEMA);
    }

    @ParameterizedTest
    @ValueSource(strings = {"claude-sonnet-4-6", "claude-opus-4-1-20250805"})
    void supportedCapabilities_preservesExplicitlyDeclaredCapabilities(String modelName) {
        AnthropicStreamingChatModel model = AnthropicStreamingChatModel.builder()
                .apiKey("dummy-key")
                .modelName(modelName)
                .supportedCapabilities(Set.of(RESPONSE_FORMAT_JSON_SCHEMA))
                .build();

        assertThat(model.supportedCapabilities()).contains(RESPONSE_FORMAT_JSON_SCHEMA);
    }
}
