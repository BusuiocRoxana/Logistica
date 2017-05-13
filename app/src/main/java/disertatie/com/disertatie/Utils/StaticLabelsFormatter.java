package disertatie.com.disertatie.Utils;

/**
 * Created by Roxana on 5/13/2017.
 */
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;

import java.util.ArrayList;

/**
 * Use this label formatter to show static labels.
 * Static labels are not bound to the data. It is typical used
 * for show text like "low", "middle", "high".
 *
 * You can set the static labels for vertical or horizontal
 * individually and you can define a label formatter that
 * is to be used if you don't define static labels.
 *
 * For example if you only use static labels for horizontal labels,
 * graphview will use the dynamicLabelFormatter for the vertical labels.
 */
public class StaticLabelsFormatter implements LabelFormatter {
    /**
     * reference to the viewport
     */
    protected Viewport mViewport;

    /**
     * the vertical labels, ordered from bottom to the top
     * if it is null, the labels will be generated via the #dynamicLabelFormatter
     */
    protected ArrayList<String> mVerticalLabels;

    /**
     * the horizontal labels, ordered form the left to the right
     * if it is null, the labels will be generated via the #dynamicLabelFormatter
     */
    protected ArrayList<String> mHorizontalLabels;

    /**
     * the label formatter that will format the labels
     * for that there are no static labels defined.
     */
    protected LabelFormatter mDynamicLabelFormatter;

    /**
     * reference to the graphview
     */
    protected final GraphView mGraphView;

    /**
     * creates the formatter without any static labels
     * define your static labels via {@link #setHorizontalLabels(ArrayList<String>)} and {@link #setVerticalLabels(ArrayList<Double>)}
     *
     * @param graphView reference to the graphview
     */
    public StaticLabelsFormatter(GraphView graphView) {
        mGraphView = graphView;
        init(null, null, null);
    }

    /**
     * creates the formatter without any static labels.
     * define your static labels via {@link #setHorizontalLabels(ArrayList<String>)} and {@link #setVerticalLabels(ArrayList<String>)}
     *
     * @param graphView reference to the graphview
     * @param dynamicLabelFormatter     the label formatter that will format the labels
     *                                  for that there are no static labels defined.
     */
    public StaticLabelsFormatter(GraphView graphView, LabelFormatter dynamicLabelFormatter) {
        mGraphView = graphView;
        init(null, null, dynamicLabelFormatter);
    }

    /**
     * creates the formatter with static labels defined.
     *
     * @param graphView reference to the graphview
     * @param horizontalLabels  the horizontal labels, ordered form the left to the right
     *                          if it is null, the labels will be generated via the #dynamicLabelFormatter
     * @param verticalLabels    the vertical labels, ordered from bottom to the top
     *                          if it is null, the labels will be generated via the #dynamicLabelFormatter
     */
    public StaticLabelsFormatter(GraphView graphView, ArrayList<String> horizontalLabels, ArrayList<String> verticalLabels) {
        mGraphView = graphView;
        init(horizontalLabels, verticalLabels, null);
    }

    /**
     * creates the formatter with static labels defined.
     *
     * @param graphView reference to the graphview
     * @param horizontalLabels  the horizontal labels, ordered form the left to the right
     *                          if it is null, the labels will be generated via the #dynamicLabelFormatter
     * @param verticalLabels    the vertical labels, ordered from bottom to the top
     *                          if it is null, the labels will be generated via the #dynamicLabelFormatter
     * @param dynamicLabelFormatter     the label formatter that will format the labels
     *                                  for that there are no static labels defined.
     */
    public StaticLabelsFormatter(GraphView graphView, ArrayList<String> horizontalLabels, ArrayList<String> verticalLabels, LabelFormatter dynamicLabelFormatter) {
        mGraphView = graphView;
        init(horizontalLabels, verticalLabels, dynamicLabelFormatter);
    }

    /**
     * @param horizontalLabels  the horizontal labels, ordered form the left to the right
     *                          if it is null, the labels will be generated via the #dynamicLabelFormatter
     * @param verticalLabels    the vertical labels, ordered from bottom to the top
     *                          if it is null, the labels will be generated via the #dynamicLabelFormatter
     * @param dynamicLabelFormatter     the label formatter that will format the labels
     *                                  for that there are no static labels defined.
     */
    protected void init(ArrayList<String> horizontalLabels, ArrayList<String> verticalLabels, LabelFormatter dynamicLabelFormatter) {
        mDynamicLabelFormatter = dynamicLabelFormatter;
        if (mDynamicLabelFormatter == null) {
            mDynamicLabelFormatter = new DefaultLabelFormatter();
        }

        mHorizontalLabels = horizontalLabels;
        mVerticalLabels = verticalLabels;
    }

    /**
     * Set a label formatter that will be used for the labels
     * that don't have static labels.
     *
     * For example if you only use static labels for horizontal labels,
     * graphview will use the dynamicLabelFormatter for the vertical labels.
     *
     * @param dynamicLabelFormatter     the label formatter that will format the labels
     *                                  for that there are no static labels defined.
     */
    public void setDynamicLabelFormatter(LabelFormatter dynamicLabelFormatter) {
        this.mDynamicLabelFormatter = dynamicLabelFormatter;
        adjust();
    }

    /**
     * @param horizontalLabels  the horizontal labels, ordered form the left to the right
     *                          if it is null, the labels will be generated via the #dynamicLabelFormatter
     */
    public void setHorizontalLabels(ArrayList<String> horizontalLabels) {
        this.mHorizontalLabels = horizontalLabels;
        adjust();
    }

    /**
     * @param verticalLabels    the vertical labels, ordered from bottom to the top
     *                          if it is null, the labels will be generated via the #dynamicLabelFormatter
     */
    public void setVerticalLabels(ArrayList<String> verticalLabels) {
        this.mVerticalLabels = verticalLabels;
        adjust();
    }

    /**
     *
     * @param value raw input number
     * @param isValueX  true if it is a value for the x axis
     *                  false if it is a value for the y axis
     * @return
     */
    @Override
    public String formatLabel(double value, boolean isValueX) {
        if (isValueX && mHorizontalLabels != null) {
            double minX = mViewport.getMinX(false);
            double maxX = mViewport.getMaxX(false);
            double range = maxX - minX;
            value = value-minX;
            int idx = (int)((value/range) * (mHorizontalLabels.size()-1));
            return mHorizontalLabels.get(idx);
        } else if (!isValueX && mVerticalLabels != null) {
            double minY = mViewport.getMinY(false);
            double maxY = mViewport.getMaxY(false);
            double range = maxY - minY;
            value = value-minY;
            int idx = (int)((value/range) * (mVerticalLabels.size()-1));
            return mVerticalLabels.get(idx);
        } else {
            return mDynamicLabelFormatter.formatLabel(value, isValueX);
        }
    }

    /**
     * @param viewport the used viewport
     */
    @Override
    public void setViewport(Viewport viewport) {
        mViewport = viewport;
        adjust();
    }

    /**
     * adjusts the number of vertical/horizontal labels
     */
    protected void adjust() {
        mDynamicLabelFormatter.setViewport(mViewport);
        if (mVerticalLabels != null) {
            if (mVerticalLabels.size() < 2) {
                throw new IllegalStateException("You need at least 2 vertical labels if you use static label formatter.");
            }
            mGraphView.getGridLabelRenderer().setNumVerticalLabels(mVerticalLabels.size());
        }
        if (mHorizontalLabels != null) {
            if (mHorizontalLabels.size() < 2) {
                throw new IllegalStateException("You need at least 2 horizontal labels if you use static label formatter.");
            }
            mGraphView.getGridLabelRenderer().setNumHorizontalLabels(mHorizontalLabels.size());
        }
    }
}
