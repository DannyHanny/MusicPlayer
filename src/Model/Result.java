package Model;

public class Result<T> {

    private T payload;
    private boolean success;
    private String error;

    public static Result Fail(String error){
        if(error.isEmpty()) {
            return Fail;
        }
        return new Result(error);
    }

    public static Result Fail = new Result("An error occurred");

    public Result(T payload)
    {
        if (payload == null) throw new NullPointerException("result");
        this.payload = payload;
        this.success = true;
    }

    private Result(String error) {
        this.error = error;
    }

    public boolean success() {
        return this.success;
    }

    public boolean fail() {
        return !this.success;
    }

    public T payload() {
        if (!this.success) throw new IllegalStateException("payload");
        return this.payload;
    }

    public String error() {
        if (this.success) {
            throw new IllegalStateException("error");
        }
        return this.error;
    }

}
