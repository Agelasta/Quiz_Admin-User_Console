import java.io.Serializable;

public class Question implements Serializable {

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private int pointer;

    public static class Builder {
        private String question;
        private String option1;
        private String option2;
        private String option3;
        private int pointer;

        public Builder question(String question) {
            this.question = question;
            return this;
        }

        public Builder option1(String option1) {
            this.option1 = option1;
            return this;
        }

        public Builder option2(String option2) {
            this.option2 = option2;
            return this;
        }

        public Builder option3(String option3) {
            this.option3 = option3;
            return this;
        }

        public Builder pointer(int pointer) {
           this.pointer = pointer;
            return this;
        }

        public Question build() {return new Question(this);}
    }

    private Question(Builder builder) {
        question = builder.question;
        option1 = builder.option1;
        option2 = builder.option2;
        option3 = builder.option3;
        pointer = builder.pointer;
    }


    public Question() {

    }

    public String getQuestion() {
        return question;
}

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public String getAnswer() {
        if (pointer == 1) return "1";
        else if (pointer == 2) return "2";
        else return "3";
    }
}
