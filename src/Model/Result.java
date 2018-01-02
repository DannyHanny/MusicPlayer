package Model;

public class Result<T> {

    private T result;
    private boolean success;

    public static Result Fail = new Result();

    public Result(T result)
    {
        if (result == null) throw new NullPointerException("result");
        this.result = result;
        this.success = true;
    }

    private Result() {}

    public boolean success() {
        return this.success;
    }

    public boolean fail() {
        return !this.success;
    }

    public T result() {
        if (!this.success) throw new IllegalStateException("result");
        return this.result;
    }

}
