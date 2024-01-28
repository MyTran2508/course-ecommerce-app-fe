package com.programming.userservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.model.BaseMapperImpl;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.userservice.domain.dto.*;
import com.programming.userservice.domain.mapper.OrderMapper;
import com.programming.userservice.domain.persistent.entity.Order;
import com.programming.userservice.domain.persistent.entity.User;
import com.programming.userservice.repository.OrdersRepository;
import com.programming.userservice.repository.UserRepository;
import com.programming.userservice.utilities.communication.CourseAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService extends BaseServiceImpl<Order, OrderDto> {
    private final OrderMapper orderMapper;

    private final CourseAPI courseAPI;

    private final OrdersRepository orderRepository;

    private final UserRepository userRepository;

    @Override
    protected BaseRepository<Order> getBaseRepository() {
        return orderRepository;
    }

    @Override
    protected BaseMapperImpl<Order, OrderDto> getBaseMapper() {
        return orderMapper;
    }

    @Override
    protected Page<OrderDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<OrderDto> getListSearchResults(String keyword) {
        return null;
    }

    @Override
    @Transactional
    public DataResponse<String> create(OrderDto dto) {
        Optional<User> optionalUser = userRepository.findById(dto.getUser().getId());

        if(optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User doesn't exists.");
        }
        DataResponse<String> response = super.create(dto);

        if(response.getStatusCode() == StatusCode.REQUEST_SUCCESS) {
            // Setup to call API add course progress
            String stResult = response.getData();

            String orderId = stResult.split(": ")[1].trim();
            Order order = orderRepository.findById(orderId).get();
            List<OrderItemDto> orderItemDtos = orderMapper.entityToDto(order).getOrderItems();

            List<String> courseIds = orderItemDtos.stream()
                    .map(OrderItemDto::getCourseId)
                    .collect(Collectors.toList());

            CourseProgressListDto courseProgressListDto = new CourseProgressListDto(order.getUser().getId(), courseIds);

            // Call API
            courseAPI.addList(courseProgressListDto);
        }
        return response;
    }

    public DataResponse<List<MonthlyStatisticDto>> getMonthlySales(int targetYear) {
        List<Object[]> totalPriceByMonth = orderRepository.getTotalPriceByMonth(targetYear);
        List<MonthlyStatisticDto> monthlyStatisticDtos = new ArrayList<>();

        for(Object[] objects: totalPriceByMonth) {
            MonthlyStatisticDto monthlyStatisticDto = new MonthlyStatisticDto((Integer) objects[0], (Double) objects[1]);
            monthlyStatisticDtos.add(monthlyStatisticDto);
        }

        for (int i = 1; i <= 12; i++) {
            int finalI = i;
            if(!monthlyStatisticDtos.stream().anyMatch(item -> item.getMonth() == finalI)) {
                monthlyStatisticDtos.add(new MonthlyStatisticDto(i, 0.0));
            }
        }

        return ResponseMapper.toDataResponseSuccess(monthlyStatisticDtos.stream()
                .sorted(Comparator.comparingInt(MonthlyStatisticDto::getMonth))
                .collect(Collectors.toList()));
    }

    public DataResponse<List<StatictisSamePeriodDto>> getSalesInSamePeriod(int targetYear) {
        List<Object[]> totalPriceByMonthTargetYear = orderRepository.getTotalPriceByMonth(targetYear);
        List<Object[]> totalPriceByMonthPreviousYear = orderRepository.getTotalPriceByMonth(targetYear - 1);
        List<StatictisSamePeriodDto> results = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            StatictisSamePeriodDto statisticSamePeriodDto = new StatictisSamePeriodDto();
            statisticSamePeriodDto.setMonth(i);

            for (Object[] objects: totalPriceByMonthTargetYear) {
                if((Integer) objects[0] == i) {
                    statisticSamePeriodDto.setTargetYearTotal((Double) objects[1]);
                }
            }
            if(statisticSamePeriodDto.getTargetYearTotal() == null) {
                statisticSamePeriodDto.setTargetYearTotal(0.0);
            }

            for (Object[] objects: totalPriceByMonthPreviousYear) {
                if((Integer) objects[0] == i) {
                    statisticSamePeriodDto.setPreviousYearTotal((Double) objects[1]);
                }
            }
            if(statisticSamePeriodDto.getPreviousYearTotal() == null) {
                statisticSamePeriodDto.setPreviousYearTotal(0.0);
            }

            results.add(statisticSamePeriodDto);
        }

        return ResponseMapper.toDataResponseSuccess(results.stream()
                .sorted(Comparator.comparingInt(StatictisSamePeriodDto::getMonth))
                .collect(Collectors.toList()));
    }

    public Double getTotalRenevueByYearAndMonth(int targetYear, Integer targetMonth) {
        return orderRepository.getTotalRenevueByYearAndMonth(targetYear, targetMonth);
    }
}
