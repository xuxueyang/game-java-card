package core.spring.service.match;

public interface MatchServiceInterface {
    public boolean join(String userId);
    boolean quit(String userId);
}
