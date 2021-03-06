package edu.sdsmt.thompsonsamson.weatherapp.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.util.JsonReader;
import android.util.Log;
import edu.sdsmt.thompsonsamson.weatherapp.IListeners;

public class Forecast implements Parcelable
{

	private static final String TAG = "";
	
	// http://developer.weatherbug.com/docs/read/WeatherBug_API_JSON
	// NOTE:  See example JSON in doc folder.
	private String _URL = "http://i.wxbug.net/REST/Direct/GetForecastHourly.ashx?zip=" + "%s" + 
	                      "&ht=t&ht=i&ht=cp&ht=fl&ht=h" + 
	                      "&api_key=zhbc4u58vr5y5zfgpwwd3rfu";
	
	// http://developer.weatherbug.com/docs/read/List_of_Icons
		
	private String _imageURL = "http://img.weather.weatherbug.com/forecast/icons/localized/500x420/en/trans/%s.png";
	
	public Forecast()
	{
	}

	public Forecast(Parcel parcel)
	{
		
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
	}

	public static final Parcelable.Creator<Forecast> Creator = new Parcelable.Creator<Forecast>()
	{
		@Override
		public Forecast createFromParcel(Parcel pc)
		{
			return new Forecast(pc);
		}
		
		@Override
		public Forecast[] newArray(int size)
		{
			return new Forecast[size];
		}
	};

	public class LoadForecast extends AsyncTask<String, Void, Forecast>
	{
		private IListeners _listener;
		private Context _context;

		private int bitmapSampleSize = -1;

		public LoadForecast(Context context, IListeners listener)
		{
			_context = context;
			_listener = listener;
		}

		protected Forecast doInBackground(String... params)
		{
			Forecast forecast = null;

			try
			{
				// HINT: You will use the following classes to make API call.
				//		 URL
				//       InputStreamReader
				//       JsonReader

			}
			catch (IllegalStateException e)
			{
				Log.e(TAG, e.toString() + params[0]);
			}
			catch (Exception e)
			{
				Log.e(TAG, e.toString());
			}

			return forecast;
		}

		protected void onPostExecute(Forecast forecast)
		{
			_listener.onForecastLoaded(forecast);
		}

		private Bitmap readIconBitmap(String conditionString, int bitmapSampleSize)
		{
			Bitmap iconBitmap = null;
			try
			{
				URL weatherURL = new URL(String.format(_imageURL, conditionString));

				BitmapFactory.Options options = new BitmapFactory.Options();
				if (bitmapSampleSize != -1)
				{
					options.inSampleSize = bitmapSampleSize;
				}

				iconBitmap = BitmapFactory.decodeStream(weatherURL.openStream(), null, options);
			}
			catch (MalformedURLException e)
			{
				Log.e(TAG, e.toString());
			}
			catch (IOException e)
			{
				Log.e(TAG, e.toString());
			}
			catch (Exception e)
			{
				Log.e(TAG, e.toString());
			}

			return iconBitmap;
		}
	}

	private byte[] convertBitmapToByteArray(Bitmap image)
	{
		// HINT:  Helper method to drive using Bitmap with the 
		//        Parcelable implementation.
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();

		try
		{
			stream.close();
		}
		catch (IOException e)
		{
			Log.e(TAG, e.toString());
		}
		finally
		{
			stream = null;
			byteArray = null;
		}

		return byteArray;
	}

	private Bitmap convertByteArrayToBitmap(byte[] data)
	{
		// HINT:  Helper method to drive using Bitmap with the 
		//        Parcelable implementation.
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		return bitmap;
	}

}
