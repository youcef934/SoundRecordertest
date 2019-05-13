package by.naxa.soundrecorder.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Environment;
import android.util.AttributeSet;

import java.io.File;

public class Paths {

    public static final String SOUND_RECORDER_FOLDER = "/SoundRecorder.by";

    Paint paint2 = new Paint();
    Paint paint1 = new Paint();
    Paint paint = new Paint();
    Path p = new Path();
    Matrix matrix = new Matrix();


    public static String combine(String parent, String... children) {
        return combine(new File(parent), children);
    }

    public static String combine(File parent, String... children) {
        File path = parent;
        for (String child : children) {
            path = new File(path, child);
        }
        return path.toString();
    }

    /**
     * Checks if external storage is available for read and write.
     */
    public static boolean isExternalStorageWritable() {
        return (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()));
    }

    /**
     * Checks if external storage is available to at least read.
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public static double getFreeStorageSpacePercent() {
        long freespace = Environment.getExternalStorageDirectory().getFreeSpace();
        long totalspace = Environment.getExternalStorageDirectory().getTotalSpace();
        return (freespace * 100.0) / (double) totalspace;
    }
    // Partie rajoutée par Baaziz et Amellal


    public PathView(Context context) {
        super(context);
        intit();
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        intit();
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intit();
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        intit();
    }

    private void intit() {

        paint.setAntiAlias(true);
        paint.setColor( Color.BLACK);
        paint.setStrokeWidth((float) 3.0);
        paint.setStyle( Paint.Style.STROKE);

        paint1.setAntiAlias(true);
        paint1.setColor( Color.GREEN);
        paint1.setStrokeWidth((float) 3.0);
        paint1.setStyle( Paint.Style.STROKE);

        paint2.setAntiAlias(true);
        paint2.setColor( Color.RED);
        paint2.setStrokeWidth((float) 3.0);
        paint2.setStyle( Paint.Style.STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectF = new RectF(454, 1113, 1035, 1213);
        canvas.drawRect(rectF, paint);
        matrix.postTranslate(20, 30);
        matrix.mapRect(rectF);
        canvas.drawRect(rectF, paint1);
        matrix.postTranslate(40, 10);
        matrix.mapRect(rectF);
        canvas.drawRect(rectF, paint2);

    }

    private void drawRect(Canvas canvas, float aX, float aY, float bX, float bY, float cX, float cY, float dX, float dY, Paint paint) {
        p.moveTo(aX, aY);
        p.lineTo(bX, bY);
        p.lineTo(cX, cY);
        p.lineTo(dX, dY);
        p.lineTo(aX, aY);
        canvas.drawPath(p, paint);
    }


}
