package com.example.project_i;

public class DataSearch {

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isStory() {
            return story;
        }

        public void setStory(boolean story) {
            this.story = story;
        }

        public DataSearch(String text) {
            this.text = text;

        }

        public DataSearch() {
        }

        private String text;
        private boolean story;

}
