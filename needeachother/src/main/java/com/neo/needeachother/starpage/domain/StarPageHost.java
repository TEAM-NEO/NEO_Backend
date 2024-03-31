package com.neo.needeachother.starpage.domain;

import com.neo.needeachother.common.enums.NEODomainType;
import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.exception.NEOExpectedException;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StarPageHost {

    @Column(name = "host_nickname")
    private String starNickName;

    @Column(name = "host_email")
    private String email;

    @ElementCollection
    @CollectionTable(name = "neo_starpage_star_type", joinColumns = @JoinColumn(name = "star_page_id"))
    @Enumerated(value = EnumType.STRING)
    private Set<StarType> starTypes;

    @ElementCollection
    @CollectionTable(name = "neo_starpage_sns_line", joinColumns = @JoinColumn(name = "star_page_id"))
    @OrderColumn(name = "sns_line_idx")
    private List<SNSLine> snsLines;

    public void isChangeable(String email) {
        if (!this.email.equals(email)) {
            throw new RuntimeException("호스트와 일치하지 않음.");
        }
    }

    // 도메인 : 스타페에지 주인의 스타 활동명을 변경할 수 있다.
    protected StarPageHost changeStarNickName(String newStarNickName) {
        return new StarPageHost(newStarNickName, this.email, this.starTypes, this.snsLines);
    }

    // 도메인 : 새로운 스타 타입을 추가할 수 있으며 중복되지 않고 순서를 유지한다.
    protected StarPageHost appendNewStarType(StarType newStarType) {
        return new StarPageHost(this.starNickName, this.email,
                this.getStarTypesAppendNewOne(this.starTypes, newStarType),
                this.snsLines);
    }

    private Set<StarType> getStarTypesAppendNewOne(Set<StarType> existingStarTypes, StarType newStarType) {
        Set<StarType> addedStarTypes = new HashSet<>(existingStarTypes);
        addedStarTypes.add(newStarType);
        return Collections.unmodifiableSet(addedStarTypes);
    }

    // 도메인 : 기존 스타 타입을 삭제할 수 있으며 최소 한 개의 타입을 유지한다.
    protected StarPageHost deleteStarType(StarType existedStarType) {
        return new StarPageHost(this.starNickName, this.email,
                getStarTypesDeleteExistedOne(this.starTypes, existedStarType)
                , this.snsLines);
    }

    private Set<StarType> getStarTypesDeleteExistedOne(Set<StarType> existingStarTypes, StarType existedStarType) throws NEOExpectedException {
        Set<StarType> deletedStarTypes = new HashSet<>(existingStarTypes);
        if (deletedStarTypes.size() == 1 && deletedStarTypes.contains(existedStarType)) {
            throw new NEOExpectedException(NEODomainType.STARPAGE,
                    NEOErrorCode.AT_LEAST_ONE_STAR_TYPE,
                    NEOErrorCode.AT_LEAST_ONE_STAR_TYPE.getErrorDescription());
        }
        deletedStarTypes.remove(existedStarType);
        return Collections.unmodifiableSet(deletedStarTypes);
    }

    // 도메인 : 새로운 SNS 정보를 추가할 수 있다.
    protected StarPageHost appendNewSNSLine(SNSLine newSNSLine) {
        return new StarPageHost(this.starNickName, this.email, this.starTypes,
                this.getSNSLinesAppendNewOne(this.snsLines, newSNSLine));
    }

    private List<SNSLine> getSNSLinesAppendNewOne(List<SNSLine> existingSNSLines, SNSLine newSNSLine) {
        List<SNSLine> addedSNSLines = new ArrayList<>(existingSNSLines);
        if (!addedSNSLines.contains(newSNSLine)) {
            addedSNSLines.add(newSNSLine);
        }
        return Collections.unmodifiableList(addedSNSLines);
    }

    protected StarPageHost deleteSNSLine(SNSLine existedSNSLine) {
        return new StarPageHost(this.starNickName, this.email, this.starTypes,
                getSNSLinesDeleteExistedOne(this.snsLines, existedSNSLine));
    }

    private List<SNSLine> getSNSLinesDeleteExistedOne(List<SNSLine> currentSNSLines, SNSLine existedSNSLine) {
        List<SNSLine> removedSNSLines = new ArrayList<>(currentSNSLines);
        removedSNSLines.remove(existedSNSLine);
        return Collections.unmodifiableList(removedSNSLines);
    }


    public static StarPageHost of(String starNickName, String email, Set<StarType> starTypes,
                                  List<SNSLine> snsLines) {
        return new StarPageHost(starNickName, email, starTypes, snsLines);
    }

}
