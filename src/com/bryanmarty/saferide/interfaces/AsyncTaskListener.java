package com.bryanmarty.saferide.interfaces;

public interface AsyncTaskListener<E,V,T> {
	public void onCancelled();
	public void onPreExecute();
	public void onProgressUpdate(V... progress);
	public void onPostExecute(T result);
}
