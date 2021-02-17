package com.appus.homeplant.users.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TermsAgreementDto {
    private Long termsId;
    private Boolean agreement;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj instanceof TermsAgreementDto) {
            TermsAgreementDto target = (TermsAgreementDto) obj;

            return termsId.equals(target.termsId);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return termsId.intValue();
    }
}
