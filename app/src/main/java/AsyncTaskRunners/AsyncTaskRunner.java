package AsyncTaskRunners;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;

import java.util.List;

/**
 * Created by derekhsieh on 10/3/15.
 * Abstract class for all task runners, easier to modulize them
 */
public abstract class AsyncTaskRunner<K,E,V> extends AsyncTask<K,E,V> {
    @Override
    protected abstract V doInBackground(K... ks);

    protected abstract boolean getClient(int params);

    protected abstract List<String> readResponse(HttpResponse response);

}
