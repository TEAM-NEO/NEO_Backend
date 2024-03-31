package com.neo.needeachother.starpage.application;

import com.neo.needeachother.starpage.domain.StarPageId;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static com.neo.needeachother.starpage.application.StarPageServiceHelper.*;

@Service
@RequiredArgsConstructor
public class StarPageViewService {

    private final StarPageRepository starPageRepository;

    public void getStarPageTopView(StarPageId id){

    }

    public void getStarPageViewByLayout(){

    }
}
