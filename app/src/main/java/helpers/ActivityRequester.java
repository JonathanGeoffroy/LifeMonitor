package helpers;

import android.app.Activity;
import android.widget.Toast;

import java.util.List;

import lifemonitor.application.lifelab.lifemonitor.R;

/**
 * Activity with HTTP request.
 *
 * @author Celia and Jonathan on 22/10/14.
 */
public abstract class ActivityRequester<T> extends Activity implements Requester<T> {

    @Override
    public void onGetResponse(List<T> response) {

    }

    @Override
    public void onAddResponse(boolean added) {

    }

    @Override
    public void onError() {
        Toast.makeText(this, R.string.db_error, Toast.LENGTH_SHORT).show();
    }
}
