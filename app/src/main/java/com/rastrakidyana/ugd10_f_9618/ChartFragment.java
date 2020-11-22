package com.rastrakidyana.ugd10_f_9618;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.rastrakidyana.ugd10_f_9618.Adapter.UserRecyclerViewAdapter;
import com.rastrakidyana.ugd10_f_9618.Database.DatabaseClient;
import com.rastrakidyana.ugd10_f_9618.Model.User;

import java.util.ArrayList;
import java.util.List;


public class ChartFragment extends Fragment {

    int FH=0, FISIP=0, FTI=0, FTB=0, FE=0, FT=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);
        APIlib.getInstance().setActiveAnyChartView(anyChartView);
        pieChart(anyChartView);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar));

        return view;
    }

    private void pieChart(final AnyChartView anyChartView){
        class GetUsers extends AsyncTask<Void, Void, List<User>> {

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);

                Pie pie = AnyChart.pie();

                pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
                    @Override
                    public void onClick(Event event) {
                        Toast.makeText(getContext(), event.getData().get("x") + " : " + event.getData().get("value"),
                                Toast.LENGTH_SHORT).show();
                    }
                });

                if(!users.isEmpty()){
                    for (int i = 0 ; i < users.size(); i++){
                        if (users.get(i).getFakultasU().equals("FTI"))
                            FTI++;
                        else if(users.get(i).getFakultasU().equals("FT"))
                            FT++;
                        else if(users.get(i).getFakultasU().equals("FH"))
                            FH++;
                        else if(users.get(i).getFakultasU().equals("FE"))
                            FE++;
                        else if(users.get(i).getFakultasU().equals("FTB"))
                            FTB++;
                        else if(users.get(i).getFakultasU().equals("FISIP"))
                            FISIP++;
                    }
                }

                List<DataEntry> data = new ArrayList<>();
                if (FTI!=0)
                    data.add(new ValueDataEntry("FTI", FTI));
                if (FT!=0)
                    data.add(new ValueDataEntry("FT", FT));
                if (FH!=0)
                    data.add(new ValueDataEntry("FH", FH));
                if (FE!=0)
                    data.add(new ValueDataEntry("FE", FE));
                if (FTB!=0)
                    data.add(new ValueDataEntry("FTB", FTB));
                if (FISIP!=0)
                    data.add(new ValueDataEntry("FISIP", FISIP));

                pie.data(data);
                pie.title("Persentase Jumlah Mahasiswa di UAJY berdasarkan Fakultas");
                pie.labels().position("outside");
                pie.legend().title().enabled(true);
                pie.legend().title()
                        .text("Nama Fakultas")
                        .padding(0d, 0d, 10d, 0d);
                pie.legend()
                        .position("center-bottom")
                        .itemsLayout(LegendLayout.HORIZONTAL)
                        .align(Align.CENTER);

                anyChartView.setChart(pie);
            }

            @Override
            protected List<User> doInBackground(Void... voids) {
                List<User> userList = DatabaseClient
                        .getInstance(getContext())
                        .getDatabase()
                        .userDAO()
                        .getAll();
                return userList;
            }
        }

        GetUsers get = new GetUsers();
        get.execute();
    }

//    private void barChart(final AnyChartView anyChartView2){
//        class GetUsers extends AsyncTask<Void, Void, List<User>> {
//
//            @Override
//            protected void onPostExecute(List<User> users) {
//                super.onPostExecute(users);
//                Cartesian cartesian = AnyChart.column();
//
//                if(!users.isEmpty()){
//                    for (int i =0 ; i < users.size(); i++){
//                        for (int j=0 ; j < jurusans.length; j ++){
//                            if(users.get(i).getJurusanU().equals(jurusans[j])){
//                                ipkPerJurusan[j] += users.get(i).getIpkU();
//                                mahasiswaPerJurusan[j] +=1;
//                            }
//                        }
//                    }
//                }
//
//                List<DataEntry> data2 = new ArrayList<>();
//                for (int i=0 ;i< jurusans.length; i++){
//                    data2.add(new ValueDataEntry(jurusans[i], ipkPerJurusan[i]/mahasiswaPerJurusan[i]));
//                }
//
//                Column column = cartesian.column(data2);
//                column.tooltip()
//                        .titleFormat("{%X}")
//                        .position(Position.CENTER_BOTTOM)
//                        .anchor(Anchor.CENTER_BOTTOM)
//                        .offsetX(0d)
//                        .offsetY(5d)
//                        .format("{%Value}{groupsSeparator : }");
//
//                cartesian.animation(true);
//                cartesian.title("Rata Rata IPK Mahasiswa FTI Pada Setiap Jurusan di UAJY");
//                cartesian.yScale().minimum(0d);
//                cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator : }");
//                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
//                cartesian.interactivity().hoverMode(HoverMode.BY_X);
//                cartesian.xAxis(0).title("Jurusan");
//                cartesian.yAxis(0).title("Rata-Rata IPK ");
//
//                anyChartView2.setChart(cartesian);
//
//            }
//
//            @Override
//            protected List<User> doInBackground(Void... voids) {
//                List<User> userList = DatabaseClient
//                        .getInstance(getContext())
//                        .getDatabase()
//                        .userDAO()
//                        .getAll();
//                return userList;
//            }
//        }
//
//        GetUsers get = new GetUsers();
//        get.execute();
//    }


}