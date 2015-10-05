package edu.calpoly.android.lab2;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class SimpleJokeList extends Activity {

	/** Contains the list Jokes the Activity will present to the user. */
	protected ArrayList<Joke> m_arrJokeList;

	/**
	 * LinearLayout used for maintaining a list of Views that each display
	 * Jokes.
	 */
	protected LinearLayout m_vwJokeLayout;

	/**
	 * EditText used for entering text for a new Joke to be added to
	 * m_arrJokeList.
	 */
	protected EditText m_vwJokeEditText;
 
	/**
	 * Button used for creating and adding a new Joke to m_arrJokeList using the
	 * text entered in m_vwJokeEditText.
	 */
	protected Button m_vwJokeButton;

	/**
	 * Background Color values used for alternating between light and dark rows
	 * of Jokes.
	 */
	protected int m_nDarkColor;
	protected int m_nLightColor;
	protected int m_nTextColor;
	protected boolean color;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// calls the initLayout
		initLayout();
		
		// creates an array of jokes to receive from resources joke array
		m_arrJokeList = new ArrayList<Joke>();
		String[] jokes = this.getResources().getStringArray(R.array.jokeList);
		
		// fulfills the array of strings with the jokes returned from resources
		for(String joke : jokes){
			Log.d("lab2igorceridorio", "Adding new joke: " + joke);
			addJoke(joke, color);
			
			// alternating the color
			if(!color)
				color = true;
			else
				color = false;
			
		}
		
		// initializing the listeners
		initAddJokeListeners();

	}

	/**
	 * Method used to encapsulate the code that initializes and sets the Layout
	 * for this Activity.
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	protected void initLayout() {

		// declaring the root ViewGroup
		LinearLayout rootLinearLayout = new LinearLayout(this);
		rootLinearLayout.setOrientation(LinearLayout.VERTICAL);
		
		// declaring the Add Joke ViewGroup
		LinearLayout addJokeLinearLayout = new LinearLayout(this);
		
		// initializing the button
		m_vwJokeButton = new Button(this);
		m_vwJokeButton.setText("Add Joke");
		
		// adding the button to the horizontal LinearLayout
		addJokeLinearLayout.addView(m_vwJokeButton);
		
		// initializing the EditText variable
		m_vwJokeEditText = new EditText(this);
		m_vwJokeEditText.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));
		m_vwJokeEditText.setInputType(InputType.TYPE_CLASS_TEXT);
		
		// adding the EditText to the horizontal view
		addJokeLinearLayout.addView(m_vwJokeEditText);
		
		// adding the horizontal LinearLayout to the root ViewGroup
		rootLinearLayout.addView(addJokeLinearLayout);
		
		// initializing the layout
		this.m_vwJokeLayout = new LinearLayout(this);
		
		// changing its orientation to vertical
		this.m_vwJokeLayout.setOrientation(LinearLayout.VERTICAL);
		
		// creating a scroll view
		ScrollView scrollView = new ScrollView(this);
		
		// adding the linear layout to the scroll view
		scrollView.addView(m_vwJokeLayout);
		
		// adding the ScrollView to the root ViewGroup
		rootLinearLayout.addView(scrollView);
		
		// providing the content which the activity is supposed to display
		setContentView(rootLinearLayout);
		
	}

	/**
	 * Method used to encapsulate the code that initializes and sets the Event
	 * Listeners which will respond to requests to "Add" a new Joke to the list.
	 */
	protected void initAddJokeListeners() {
		
		// calling the listener to the button
		m_vwJokeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				// get the text of the new joke from the EditText 
				String newJoke = m_vwJokeEditText.getText().toString();
				
				// if the string contains something we add it to the ArrayList
				if(!newJoke.isEmpty()){
					addJoke(newJoke, color);
				}
				
				// alternating the color
				if(!color)
					color = true;
				else
					color = false;
				
				// hiding the Soft Keyboard
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(m_vwJokeEditText.getApplicationWindowToken(), 0);
				
			}
		});
		
		// calling the listener for specific keys pressed
		m_vwJokeEditText.setOnKeyListener(new OnKeyListener()
		{
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				if (event.getAction() == KeyEvent.ACTION_DOWN
						&& (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER))
				{
					
					// get the text of the new joke from the EditText 
					String newJoke = m_vwJokeEditText.getText().toString();
					
					// if the string contains something we add it to the ArrayList
					if(!newJoke.isEmpty()){
						addJoke(newJoke, color);
					}
					
					// alternating the color
					if(!color)
						color = true;
					else
						color = false;
					
					return true;
				}
				
				return false;
			}

		});
		
	}

	/**
	 * Method used for encapsulating the logic necessary to properly initialize
	 * a new joke, add it to m_arrJokeList, and display it on screen.
	 * 
	 * @param strJoke
	 *            A string containing the text of the Joke to add.
	 */
	@SuppressWarnings("deprecation")
	protected void addJoke(String strJoke, boolean color) {
		
		// retrieving the colors and storing them in the variables
		m_nDarkColor = getResources().getColor(R.color.dark);
		m_nLightColor = getResources().getColor(R.color.light);
		m_nTextColor = getResources().getColor(R.color.text);
		
		
		// adding the joke text to a TextView object
		TextView newJoke = new TextView(this);
		newJoke.setTextColor(m_nTextColor);
		newJoke.setText(strJoke);
		
		// adding the TextView to the layout
		m_vwJokeLayout.addView(newJoke);
		
		// alternating between the background colors
		if(!color)
			newJoke.setBackgroundColor(m_nDarkColor);
		else
			newJoke.setBackgroundColor(m_nLightColor);
		
	}
}