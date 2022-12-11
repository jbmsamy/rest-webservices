package com.in28minutes.rest.webservices.user.filtering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilterController {

	@GetMapping("/filtering")
	public MappingJacksonValue filtering() 	{
		SomeBean someBean = new  SomeBean("Vlae1", "Value2","Value3");
		MappingJacksonValue mappingJacksonValue = createSomeBeanFilter(someBean,"filed1","field3");/*new MappingJacksonValue(someBean); 
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field3");
				
		SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		mappingJacksonValue.setFilters(filterProvider); */
		return mappingJacksonValue;
	}
	
	@GetMapping("/filtering-list2")
	public List<MappingJacksonValue> filteringList() {
		
		List<SomeBean> beanList=  Arrays.asList(  new SomeBean("value1", "Value2", "value3"), 
			new SomeBean("value4", "Value5", "value6"));
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field3");
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		List<MappingJacksonValue> filteredList = new ArrayList<MappingJacksonValue>();
		for(SomeBean bean:beanList) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(bean);
			mappingJacksonValue.setFilters(filterProvider);
			filteredList.add(mappingJacksonValue);
		}
		return filteredList;
	}
	
	@GetMapping("/filtering-list")
	public MappingJacksonValue dynamicFilteringList() {
		List<SomeBean> beanList=  Arrays.asList(  new SomeBean("value1", "Value2", "value3"), 
				new SomeBean("value4", "Value5", "value6"));
		MappingJacksonValue mappingJacksonValue = createSomeBeanFilter(beanList,"field1","field2");
		return mappingJacksonValue;
	}
	private MappingJacksonValue createSomeBeanFilter(Object bean, String... fields) {
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(bean); 
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(fields);
				
		SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}
}
