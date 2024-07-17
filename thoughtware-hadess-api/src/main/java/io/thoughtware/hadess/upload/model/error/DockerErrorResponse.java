package io.thoughtware.hadess.upload.model.error;

import java.util.List;

public class DockerErrorResponse {
    private List<ErrorDetail> errors;

    public static class ErrorDetail {
        private String code;
        private String message;
        private List<DetailItem> detail;

        public static class DetailItem {
            private String Name;
            private String Tag;

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            public String getTag() {
                return Tag;
            }

            public void setTag(String tag) {
                Tag = tag;
            }

            // getters and setters
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<DetailItem> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailItem> detail) {
            this.detail = detail;
        }
        // getters and setters
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
    }
}
