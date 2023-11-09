package com.programming.userservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.model.BaseMapperImpl;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.userservice.communication.OpenFeign.CourseAccessApi;
import com.programming.userservice.domain.dto.CourseAccessListDto;
import com.programming.userservice.domain.dto.OrderDto;
import com.programming.userservice.domain.dto.OrderItemDto;
import com.programming.userservice.domain.mapper.OrderMapper;
import com.programming.userservice.domain.persistent.entity.Order;
import com.programming.userservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService extends BaseServiceImpl<Order, OrderDto> {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CourseAccessApi courseAccessApi;

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
    public DataResponse<OrderDto> create(OrderDto dto) {
        DataResponse<OrderDto> response = super.create(dto);
        if(response.getStatusCode() == StatusCode.REQUEST_SUCCESS) {

            // Setup to call API add course access
            String userId = response.getData().getUser().getId();
            List<OrderItemDto> orderItemDtos = response.getData().getOrderItems();
            List<String> courseIds = orderItemDtos.stream()
                    .map(OrderItemDto::getCourseId)
                    .collect(Collectors.toList());
            CourseAccessListDto courseAccessListDto = new CourseAccessListDto(userId, courseIds);

            // Call API
            courseAccessApi.addList(courseAccessListDto);
        }
        return response;
    }
}
