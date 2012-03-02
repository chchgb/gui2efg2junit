package com.nofatclips.androidtesting.guitree;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nofatclips.androidtesting.model.ActivityState;

public class StartActivity extends TestCaseActivity {

	public StartActivity(Element item) {
		super (item);
	}

	public final static String getTag () {
		return "START_ACTIVITY";
	}
	
	public static StartActivity createActivity (GuiTree session) {
		return createActivity (session.getDom());
	}
	
	public static StartActivity createActivity (Document dom) {
		return createActivity (dom, getTag());
	}

	public static StartActivity createActivity (Document dom, String tag) {
		Element el = dom.createElement(tag);
		Element desc = dom.createElement(DESC_TAG);
		el.appendChild(desc);
		return new StartActivity (el);		
	}
	
	public static StartActivity createActivity (ActivityState originalActivity) {
		Document dom = originalActivity.getElement().getOwnerDocument();
		StartActivity newActivity = createActivity (dom);
		if (originalActivity instanceof TestCaseActivity) {
			TestCaseActivity original = (TestCaseActivity)originalActivity; 
			newActivity.setUniqueId(original.getUniqueId());
			newActivity.setScreenshot(original.getScreenshot());
		}
		newActivity.setName(originalActivity.getName());
		newActivity.setTitle(originalActivity.getTitle());
		newActivity.setId(originalActivity.getId());
		newActivity.copyDescriptionFrom(originalActivity);
		return newActivity;
	}

	public StartActivity clone () {
		return createActivity(this);
	}
	
}
