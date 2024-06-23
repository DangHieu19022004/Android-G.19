package com.example.appdocsach.model;

public class DialogFlowRequest {
    public QueryInput queryInput;

    public DialogFlowRequest(QueryInput queryInput) {
        this.queryInput = queryInput;
    }

    public static class QueryInput {
        public TextInput text;

        public QueryInput(TextInput text) {
            this.text = text;
        }
    }

    public static class TextInput {
        public String text;
        public String languageCode;

        public TextInput(String text, String languageCode) {
            this.text = text;
            this.languageCode = languageCode;
        }

        public void QueryInput(TextInput text) {
            this.text = text.text;
            this.languageCode = text.languageCode;
        }
    }
}
