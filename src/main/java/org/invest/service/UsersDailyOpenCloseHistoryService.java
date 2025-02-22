package org.invest.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.invest.dto.DailyDtoBetweenDates;
import org.invest.dto.HistoryDto;
import org.invest.entity.DailyOpenClose;
import org.invest.entity.User;
import org.invest.entity.UsersDailyOpenCloseHistory;
import org.invest.repository.UserRepository;
import org.invest.repository.UsersDailyOpenCloseHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersDailyOpenCloseHistoryService {
    private final UsersDailyOpenCloseHistoryRepository historyRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<DailyOpenClose> getDailyOpenClose(DailyDtoBetweenDates betweenDates, Long userId) {
        HistoryDto historyDto = getHistoryDto(userId,
                                              betweenDates.getTicker(),
                                              betweenDates.getOpenDate(),
                                              betweenDates.getOpenDate().plusDays(betweenDates.getCountDay()-1));

        List<UsersDailyOpenCloseHistory> historyList = historyRepository.getHistoryByFullHistoryDto(historyDto);

        return historyList.stream()
                .map(UsersDailyOpenCloseHistory::getDailyOpenClose)
                .toList();
    }

    @Transactional
    public List<DailyOpenClose> getUserDailyOpenCloseHistory(Long userId,String symbol) {
        HistoryDto historyDto = getHistoryDto(userId, symbol,null,null);
        List<UsersDailyOpenCloseHistory> historyList =
                historyRepository.getHistory(historyDto);
        return getDailyOpenClose(historyList);
    }

    public boolean existsUserDailyOpenCloseHistory(Long userId, String symbol, LocalDate dateFrom) {
        HistoryDto historyDto = getHistoryDto(userId, symbol,dateFrom,null);
        return historyRepository.isHistoryEmpty(historyDto);
    }

    public void saveUserDailyOpenCloseHistory(Long userId, DailyOpenClose dailyOpenClose) {
        User user = userRepository.getUserById(userId);

        historyRepository.save(UsersDailyOpenCloseHistory.builder()
                .dailyOpenClose(dailyOpenClose)
                .user(user)
                .dateCreated(LocalDateTime.now())
                .build());
    }

    private List<DailyOpenClose> getDailyOpenClose(List<UsersDailyOpenCloseHistory> historyList){
        List<DailyOpenClose> dailyOpenCloseList = new ArrayList<>();

        for(UsersDailyOpenCloseHistory history : historyList) {
            dailyOpenCloseList.add(history.getDailyOpenClose());
        }
        return dailyOpenCloseList;
    }

    private HistoryDto getHistoryDto(Long userId, String symbol, LocalDate from, LocalDate to) {
        return HistoryDto.builder()
                .userId(userId)
                .symbol(symbol)
                .from(from)
                .to(to)
                .build();
    }
}
