package kr.co.ehr.chart.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import kr.co.ehr.chart.service.Line;
import kr.co.ehr.chart.service.Pie;

@Controller
public class ChartController {
	
	Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	
	@RequestMapping(value="chart/line_chart_view.do",method = RequestMethod.GET)
	public String doLineView() {
		LOG.debug("=========================");
		LOG.debug("=@Controller=doLineView==");
		LOG.debug("=========================");
		return "chart/line_chart";
	}
	
	@RequestMapping(value="chart/pie_chart_view.do",method = RequestMethod.GET)
	public String doPieView() {
		LOG.debug("=========================");
		LOG.debug("=@Controller=doPieView==");
		LOG.debug("=========================");
		return "chart/pie_chart";
	}
	
	@RequestMapping(value="chart/pie_chart.do",method = RequestMethod.GET ,produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String pie_chart(Model model) {

		Pie  out01=new Pie("공부",11);
		Pie  out02=new Pie("식사",2);
		Pie  out03=new Pie("Commute",2);
		Pie  out04=new Pie("Watch TV",2);
		Pie  out05=new Pie("잠",7);
		
		List<Pie> list=new ArrayList<Pie>();
		list.add(out01);
		list.add(out02);
		list.add(out03);
		list.add(out04);
		list.add(out05);
		
		Gson gson=new Gson();
		JsonArray  jArray=new JsonArray();
		
		for(int i=0;i<list.size();i++) {
			JsonArray  sArray=new JsonArray();
			sArray.add(list.get(i).getTask());
			sArray.add(list.get(i).getHoursPerDay());
			
			jArray.add(sArray);
			//[[],
			// [],
			// [],
			//]
		}
		
		LOG.debug("============================");
		LOG.debug("jArray="+jArray.toString());
		LOG.debug("============================");
		
		return jArray.toString();
		
	}
	
	@RequestMapping(value="chart/line_chart.do", method=RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String line_chart(Model model) {
		
		Line out01 = new Line("2015",1000,400);
		Line out02 = new Line("2016",1170,460);
		Line out03 = new Line("2017",660,1120);
		Line out04 = new Line("2018",1030,540);
		
		List<Line> list = new ArrayList<Line>();
		list.add(out01);
		list.add(out02);
		list.add(out03);
		list.add(out04);
		
		Gson gson = new Gson();
		JsonArray jArray = new JsonArray();
		
		for(int i=0; i< list.size(); i++) {
			JsonArray sArray = new JsonArray();
			sArray.add(list.get(i).getYear());
			sArray.add(list.get(i).getSales());
			sArray.add(list.get(i).getExpenses());
			
			//[ [],[],[] ]
			jArray.add(sArray);
			
		}
		LOG.debug("====================================");
		LOG.debug("=jArray=" + jArray.toString());
		LOG.debug("====================================");
		
		return jArray.toString();
		
	}

}
