package org.invest.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.invest.dto.DailyOpenCloseDto;
import org.invest.dto.DailyOpenCloseDtoBetweenDates;
import org.invest.entity.DailyOpenClose;
import org.invest.entity.User;
import org.invest.entity.UsersDailyOpenCloseHistory;
import org.invest.repository.UserRepository;
import org.invest.repository.UsersDailyOpenCloseHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersDailyOpenCloseHistoryService {
    private final UsersDailyOpenCloseHistoryRepository usersDailyOpenCloseHistoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<DailyOpenClose> getDailyOpenClose(DailyOpenCloseDtoBetweenDates betweenDates,
                                                  Long userId) {
        List<UsersDailyOpenCloseHistory> historyList =usersDailyOpenCloseHistoryRepository
                .getUsersDailyOpenCloseHistoriesByUserIdAndSymbolAndDateFromBetween(userId,
                                                                                    betweenDates.getTicker(),
                                                                                    betweenDates.getOpenDate(),
                                                                                    betweenDates.getCloseDate());

        return getDailyOpenClose(historyList);
    }

    @Transactional
    public List<DailyOpenClose> getUserDailyOpenCloseHistory(Long userId,String symbol) {
        List<UsersDailyOpenCloseHistory> historyList =
                usersDailyOpenCloseHistoryRepository.getUsersDailyOpenCloseHistoriesByUserIdAndSymbol(userId,symbol);
        return getDailyOpenClose(historyList);
    }

    public void saveListDailyOpenCloseHistory(List<DailyOpenClose> usersDailyOpenCloseList,
                                              Long userId) {

        User user = userRepository.getUserById(userId);

        for(DailyOpenClose dailyOpenClose : usersDailyOpenCloseList) {
            usersDailyOpenCloseHistoryRepository.save(UsersDailyOpenCloseHistory.builder()
                    .dailyOpenClose(dailyOpenClose)
                    .user(user)
                    .dateCreated(LocalDateTime.now())
                    .build());
        }
    }

    private List<DailyOpenClose> getDailyOpenClose(List<UsersDailyOpenCloseHistory> historyList){
        List<DailyOpenClose> dailyOpenCloseList = new ArrayList<>();

        for(UsersDailyOpenCloseHistory history : historyList) {
            dailyOpenCloseList.add(history.getDailyOpenClose());
        }
        return dailyOpenCloseList;
    }
}
