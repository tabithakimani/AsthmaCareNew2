package tabitha.com.asthmacarenew;

public class Sound {
    public String soundlevel;
    public String led;

    public Sound(String soundlevel, String led) {
        this.soundlevel = soundlevel;
        this.led = led;
    }

    public Sound() {
    }

    public String getSoundlevel() {
        return soundlevel;
    }

    public void setSoundlevel(String soundlevel) {
        this.soundlevel = soundlevel;
    }

    public String getLed() {
        return led;
    }

    public void setLed(String led) {
        this.led = led;
    }
}
