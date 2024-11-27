package oriane.terence.paul;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public enum AppPreferences {
    musicIsPlaying("Music Enabled"),
    fullScreenIsEnabled("Full Screen Enabled"),
    windowScreenIsEnabled("Window Screen Enabled"),
    smallScreenIsEnabled("Small Screen Enabled"),
    name("Reptile Rush");

    private final String value;

    private AppPreferences(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    protected Preferences getPreferences() {
        // Implementation for getting Preferences
        return Gdx.app.getPreferences(AppPreferences.name.toString());
    }

    public Boolean isEnabled() {
        // Implementationor getting Preferences
        return getPreferences().getBoolean(this.value, false);
    }

    public float getPreference() {
        return getPreferences().getFloat(value, 0.5f);
    }

    public void setPreferences(boolean bool) {
        getPreferences().putBoolean(this.value, bool);
        getPreferences().flush();
    }

    public void setPreferences(float vol) {
        getPreferences().putFloat(this.value, vol);
        getPreferences().flush();
    }

    public Preferences putBoolean(String key, boolean val) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putBoolean'");
    }

    public Preferences putInteger(String key, int val) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putInteger'");
    }

    public Preferences putLong(String key, long val) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putLong'");
    }


    public Preferences putFloat(String key, float val) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putFloat'");
    }

    public Preferences putString(String key, String val) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putString'");
    }

    public Preferences put(Map<String, ?> vals) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'put'");
    }

    public boolean getBoolean(String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBoolean'");
    }

    public int getInteger(String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInteger'");
    }

    public long getLong(String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLong'");
    }

    public float getFloat(String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFloat'");
    }

    public String getString(String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getString'");
    }

    public boolean getBoolean(String key, boolean defValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBoolean'");
    }

    public int getInteger(String key, int defValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInteger'");
    }

    public long getLong(String key, long defValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLong'");
    }

    public float getFloat(String key, float defValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFloat'");
    }

    public String getString(String key, String defValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getString'");
    }

    public Map<String, ?> get() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    public boolean contains(String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'contains'");
    }

    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    public void remove(String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    public void flush() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'flush'");
    }


}
