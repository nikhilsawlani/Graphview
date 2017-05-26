package lifesaver.example.nikhil.graphview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private LineChart lineChart;
    private Firebase mref;
    private Float yvalues[]=new Float[5];
    private String xvalues[]=new String[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineChart=(LineChart)findViewById(R.id.line_chart);
        //ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mrecords);
        mref=new Firebase("https://fir-basic-a61d8.firebaseio.com/month");
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,String> map=dataSnapshot.getValue(Map.class);


                 xvalues = new String[]{map.get("jan"), map.get("feb"), map.get("mar"), map.get("apr"), map.get("dec")};

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mref=new Firebase("https://fir-basic-a61d8.firebaseio.com/returns");
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,Float> map=dataSnapshot.getValue(Map.class);
                yvalues=new Float[]{Float.valueOf(String.valueOf(map.get("jan"))),Float.valueOf(String.valueOf( map.get("feb"))),Float.valueOf(String.valueOf( map.get("mar"))), Float.valueOf(String.valueOf(map.get("apr"))),Float.valueOf(String.valueOf( map.get("dec")))};
                Drawlinechart(yvalues,xvalues);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



    }
    private void Drawlinechart(Float[] yvalues, String [] xvalues){
        YAxis yAxis =lineChart.getAxisLeft();
        YAxis y2axsis=lineChart.getAxisRight();
        y2axsis.setDrawAxisLine(false);
        lineChart.getAxisRight().setEnabled(false);
        yAxis.setAxisMinValue(15);
        yAxis.setAxisMaxValue(27);

        yAxis.setGranularity(2);
        lineChart.setBackgroundColor(Color.TRANSPARENT);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setDragEnabled(true);
        lineChart.setDescription("Returns");
        ArrayList<Entry> ydata=new ArrayList<>();
        for(int i=0;i<yvalues.length;i++){

            ydata.add(new Entry(yvalues[i],i));
        }
        ArrayList<String> xdata=new ArrayList<>();
        for (int i =0;i<xvalues.length;i++){

         xdata.add(xvalues[i] );
        }



        LineDataSet lineDataSet = new LineDataSet(ydata, "Returns");
        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

         LineData lineData =new  LineData(xdata,lineDataSet);

        lineData.setValueTextSize(13f);

        lineData.setValueTextColor(Color.WHITE);

        lineChart.setData(lineData);
        lineChart.invalidate();

    }
}
