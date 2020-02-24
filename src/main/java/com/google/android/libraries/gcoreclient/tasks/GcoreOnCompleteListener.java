package com.google.android.libraries.gcoreclient.tasks;

public interface GcoreOnCompleteListener<ResultT> {
    void onComplete(GcoreTask<ResultT> gcoreTask);
}
