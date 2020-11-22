package com.rastrakidyana.ugd10_f_9618;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.rastrakidyana.ugd10_f_9618.Database.DatabaseClient;
import com.rastrakidyana.ugd10_f_9618.Model.User;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment2 extends Fragment {
    String[] jurusans = {"Informatika", "Sistem Informasi", "Teknik Industri"};
    Double[] ipkPerJurusan = {0.0,0.0,0.0};
    Double[] mahasiswaPerJurusan = {0.0,0.0,0.0};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chart_2, container, false);

        AnyChartView anyChartView2 = view.findViewById(R.id.any_chart_view2);
        APIlib.getInstance().setActiveAnyChartView(anyChartView2);
        barChart(anyChartView2);
        anyChartView2.setProgressBar(view.findViewById(R.id.progress_bar2));

        return view;
    }

    private void barChart(final AnyChartView anyChartView2){
        class GetUsers extends AsyncTask<Void, Void, List<User>> {

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);
                Cartesian cartesian = AnyChart.column();

                if(!users.isEmpty()){
                    for (int i =0 ; i < users.size(); i++){
                        for (int j=0 ; j < jurusans.length; j ++){
                            if(users.get(i).getJurusanU().equals(jurusans[j])){
                                ipkPerJurusan[j] += users.get(i).getIpkU();
                                mahasiswaPerJurusan[j] +=1;
                            }
                        }
                    }
                }

                List<DataEntry> data2 = new ArrayList<>();
                for (int i=0 ;i< jurusans.length; i++){
                    data2.add(new ValueDataEntry(jurusans[i], ipkPerJurusan[i]/mahasiswaPerJurusan[i]));
                }

                Column column = cartesian.column(data2);
                column.tooltip()
                        .titleFormat("{%X}")
                        .position(Position.CENTER_BOTTOM)
                        .anchor(Anchor.CENTER_BOTTOM)
                        .offsetX(0d)
                        .offsetY(5d)
                        .format("{%Value}{groupsSeparator : }");

                cartesian.animation(true);
                cartesian.title("Rata Rata IPK Mahasiswa FTI Pada Setiap Jurusan di UAJY");
                cartesian.yScale().minimum(0d);
                cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator : }");
                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                cartesian.interactivity().hoverMode(HoverMode.BY_X);
                cartesian.xAxis(0).title("Jurusan");
                cartesian.yAxis(0).title("Rata-Rata IPK ");

                anyChartView2.setChart(cartesian);

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

}
