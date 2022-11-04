package com.coffekyun.cinema.service;

import com.coffekyun.cinema.model.dto.*;

import java.util.List;

public interface MovieService {

    MovieResponse add(MovieRequest movieRequest);

    MovieUpdateResponse update(MovieUpdateRequest movieUpdateRequest, String id);

    void delete(String id);

    List<MovieIsShowingResponse> isShowing(Boolean status);

    MovieScheduleResponse showSchedule(String id);


}
