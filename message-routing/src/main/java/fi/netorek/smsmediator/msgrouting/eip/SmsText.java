package fi.netorek.smsmediator.msgrouting.eip;

public class SmsText {
    private String routeKey;
    private String text;

    public SmsText(String routeKey, String text) {
        this.routeKey = routeKey;
        this.text = text;
    }

    public String getRouteKey() {
        return routeKey;
    }

    public String getText() {
        return text;
    }
}
