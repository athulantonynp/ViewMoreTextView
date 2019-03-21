package app.expandable.edittext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Athul Antony for Entri.app
 */

public class ExpandableTextView extends android.support.v7.widget.AppCompatTextView {

    private Integer seeMoreTextColor = R.color.brand_green;

    private String collapsedTextWithSeeMoreButton;

    private String orignalContent;

    private SpannableString collapsedTextSpannable;

    private String seeMore = "View More";

    private boolean hasInitiated=false;

    ClickableSpan clickableSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget)
        {
            expand();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.setColor(getResources().getColor(seeMoreTextColor));
        }
    };

    /*
    By default it will be set to 0
     */

    private  int shortenedLineCount=0;

    public ExpandableTextView(Context context)
    {
        super(context);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSeeMoreTextColor(Integer color)
    {
        seeMoreTextColor = color;
    }

    public void setSeeMoreText(String seeMoreText)
    {
        seeMore = seeMoreText;
    }


    public void expand()
    {
        setText(orignalContent);
    }

    public  void  setTextContent(String text,int shortenedLineCount){
        this.shortenedLineCount=shortenedLineCount;
        orignalContent = text;
        hasInitiated=true;
        setText(text);
    }

    private void setContent(int originalLinesCount) {

            this.setMovementMethod(LinkMovementMethod.getInstance());
            int textMaxLength = ((getText().length() / originalLinesCount) * shortenedLineCount)-(seeMore.length());
            hasInitiated=false;
            if (originalLinesCount > shortenedLineCount) {
                collapsedTextWithSeeMoreButton = orignalContent.substring(0, textMaxLength) + "... " + seeMore;
                collapsedTextSpannable = new SpannableString(collapsedTextWithSeeMoreButton);

                collapsedTextSpannable.setSpan(clickableSpan, textMaxLength + 4, collapsedTextWithSeeMoreButton.length(), 0);
                collapsedTextSpannable.setSpan(new StyleSpan(Typeface.NORMAL), textMaxLength + 4, collapsedTextWithSeeMoreButton.length(), 0);
                collapsedTextSpannable.setSpan(new RelativeSizeSpan(.9f), textMaxLength + 4, collapsedTextWithSeeMoreButton.length(), 0);
                setText(collapsedTextSpannable);
            }else {
                setText(orignalContent);
            }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(hasInitiated){
            setContent(getLineCount());
        }
    }

}
