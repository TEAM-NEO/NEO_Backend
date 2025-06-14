import random
import string
import json
from datetime import datetime, timedelta
import math
import numpy as np
import os


class NEODummyDataGenerator:
    def __init__(self):
        self.fan_count = 2_000_000
        self.star_count = 10_000
        self.batch_size = 10_000
        self.output_dir = "sql_output"  # SQL 파일 저장 디렉터리

        # 스타 유형 (NEOStarTypeEntity 참고)
        self.star_types = ['YOUTUBER', 'SINGER', 'ACTOR', 'SNS_INFLUENCER']

        # 성별 코드 (NEOGenderType 참고)
        self.genders = ['M', 'F', 'NONE']

        # 소셜 로그인 타입 (NEOOAuth2ProviderType 참고)
        self.provider_types = ['kakao', 'naver', 'google', None]

        # 생성된 데이터 추적
        self.created_fan_ids = []
        self.created_star_ids = []
        self.created_star_info_ids = []

        # 출력 디렉터리 생성
        self._ensure_output_directory()

    def _ensure_output_directory(self):
        """SQL 파일을 저장할 디렉터리가 없으면 생성"""
        if not os.path.exists(self.output_dir):
            os.makedirs(self.output_dir)
            print(f"디렉터리 생성됨: {self.output_dir}")

    def generate_random_string(self, length=10):
        """랜덤 문자열 생성"""
        return ''.join(random.choices(string.ascii_lowercase + string.digits, k=length))

    def generate_korean_name(self):
        """한국어 이름 생성"""
        last_names = ['김', '이', '박', '최', '정', '강', '조', '윤', '장', '임']
        first_names = ['민수', '지은', '서준', '서연', '하준', '지우', '지훈', '수빈', '예준', '서윤']
        return random.choice(last_names) + random.choice(first_names)

    def generate_phone_number(self):
        """한국 휴대폰 번호 생성"""
        return f"010-{random.randint(1000, 9999)}-{random.randint(1000, 9999)}"

    def generate_timestamp(self):
        """타임스탬프 생성"""
        base_date = datetime(2023, 1, 1)
        random_days = random.randint(0, 365)
        return (base_date + timedelta(days=random_days)).strftime('%Y-%m-%d %H:%M:%S')

    def generate_fans_sql(self):
        """팬 계정 200만개 생성 SQL"""
        print("팬 계정 200만개 생성 중...")

        file_path = os.path.join(self.output_dir, '01_insert_fan_users.sql')
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write("-- 팬 계정 200만개 생성\n")
            f.write("SET FOREIGN_KEY_CHECKS = 0;\n")
            f.write("SET UNIQUE_CHECKS = 0;\n")
            f.write("SET AUTOCOMMIT = 0;\n\n")

            for batch_start in range(0, self.fan_count, self.batch_size):
                batch_end = min(batch_start + self.batch_size, self.fan_count)
                values = []

                for i in range(batch_start, batch_end):
                    fan_id = i + 1
                    self.created_fan_ids.append(fan_id)

                    neo_id = f"fan_{fan_id}"
                    email = f"fan{fan_id}@example.com"
                    password = self.generate_random_string(20)  # 랜덤 패스워드
                    phone = self.generate_phone_number()
                    nickname = f"팬_{fan_id}"
                    username = self.generate_korean_name()
                    gender = random.choice(self.genders)
                    provider = random.choice(self.provider_types)
                    social_id = f"social_{fan_id}" if provider else None
                    created_at = self.generate_timestamp()

                    value = f"({fan_id}, 'F', '{neo_id}', "
                    value += "NULL, " if not social_id else f"'{social_id}', "
                    value += f"'{username}', '{password}', '{email}', '{phone}', "
                    value += "NULL, " if not provider else f"'{provider}', "
                    value += f"'{nickname}', '{gender}', NULL, '{created_at}', '{created_at}')"
                    values.append(value)

                f.write(
                    "INSERT INTO user (id, role_type, neo_id, social_id, user_name, password, email, phone_number, provider_type, neo_nick_name, gender, star_info_id, created_at, updated_at) VALUES\n")
                f.write(",\n".join(values))
                f.write(";\n\n")

                if (batch_end % 100000) == 0:
                    print(f"  {batch_end:,} / {self.fan_count:,} 팬 계정 생성됨")

            f.write("COMMIT;\n")
            f.write("SET FOREIGN_KEY_CHECKS = 1;\n")
            f.write("SET UNIQUE_CHECKS = 1;\n")
            f.write("SET AUTOCOMMIT = 1;\n")

    def generate_star_info_sql(self):
        """star_info 10,000개 생성 SQL"""
        print("star_info 10,000개 생성 중...")

        file_path = os.path.join(self.output_dir, '02_insert_star_info.sql')
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write("-- star_info 10,000개 생성\n")
            f.write("SET FOREIGN_KEY_CHECKS = 0;\n\n")

            values = []
            for i in range(self.star_count):
                star_info_id = i + 1
                self.created_star_info_ids.append(star_info_id)

                star_nickname = f"스타_{star_info_id}"

                value = f"({star_info_id}, '{star_nickname}')"
                values.append(value)

            f.write("INSERT INTO star_info (id, star_nickname) VALUES\n")
            f.write(",\n".join(values))
            f.write(";\n\n")

            f.write("SET FOREIGN_KEY_CHECKS = 1;\n")

    def generate_star_users_sql(self):
        """스타 계정 10,000개 생성 SQL (star_info와 1:1 매핑)"""
        print("스타 계정 10,000개 생성 중...")

        file_path = os.path.join(self.output_dir, '03_insert_star_users.sql')
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write("-- 스타 계정 10,000개 생성 (star_info와 1:1 매핑)\n")
            f.write("SET FOREIGN_KEY_CHECKS = 0;\n\n")

            # 팬 계정 이후의 ID부터 시작
            star_id_start = self.fan_count + 1

            values = []
            for i in range(self.star_count):
                star_id = star_id_start + i
                star_info_id = i + 1  # star_info의 ID와 매칭
                self.created_star_ids.append(star_id)

                neo_id = f"star_{i + 1}"
                email = f"star{i + 1}@example.com"
                password = self.generate_random_string(20)
                phone = self.generate_phone_number()
                nickname = f"스타닉네임_{i + 1}"
                username = self.generate_korean_name()
                gender = random.choice(self.genders)
                provider = random.choice(self.provider_types)
                social_id = f"star_social_{i + 1}" if provider else None
                created_at = self.generate_timestamp()

                value = f"({star_id}, 'S', '{neo_id}', "
                value += "NULL, " if not social_id else f"'{social_id}', "
                value += f"'{username}', '{password}', '{email}', '{phone}', "
                value += "NULL, " if not provider else f"'{provider}', "
                value += f"'{nickname}', '{gender}', {star_info_id}, '{created_at}', '{created_at}')"
                values.append(value)

            f.write(
                "INSERT INTO user (id, role_type, neo_id, social_id, user_name, password, email, phone_number, provider_type, neo_nick_name, gender, star_info_id, created_at, updated_at) VALUES\n")
            f.write(",\n".join(values))
            f.write(";\n\n")

            f.write("SET FOREIGN_KEY_CHECKS = 1;\n")

    def generate_star_types_sql(self):
        """스타 유형 데이터 생성 SQL"""
        print("스타 유형 데이터 생성 중...")

        file_path = os.path.join(self.output_dir, '04_insert_star_types.sql')
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write("-- 스타 유형 데이터 생성\n")
            f.write("SET FOREIGN_KEY_CHECKS = 0;\n\n")

            values = []
            type_id = 1

            for star_info_id in self.created_star_info_ids:
                # 각 스타는 1~3개의 유형을 가짐
                num_types = random.randint(1, 3)
                selected_types = random.sample(self.star_types, num_types)

                for star_type in selected_types:
                    # NEOStarDetailClassification의 코드 매핑
                    type_code_map = {
                        'YOUTUBER': 'YO',
                        'SINGER': 'SI',
                        'ACTOR': 'AC',
                        'SNS_INFLUENCER': 'SN'
                    }

                    value = f"({type_id}, {star_info_id}, '{type_code_map[star_type]}')"
                    values.append(value)
                    type_id += 1

            f.write("INSERT INTO star_type (id, star_id, star_type) VALUES\n")
            f.write(",\n".join(values))
            f.write(";\n\n")

            f.write("SET FOREIGN_KEY_CHECKS = 1;\n")

    def generate_follow_relationships_sql(self):
        """팔로우 관계 생성 SQL"""
        print("팔로우 관계 생성 중...")

        # 팔로워 수 분포 생성 (상위 50%는 약 10만명)
        follower_counts = []
        for i in range(self.star_count):
            if i < self.star_count // 2:  # 상위 50%
                # 8만 ~ 12만 사이의 팔로워
                count = random.randint(80000, 120000)
            else:  # 하위 50%
                # 100 ~ 50000 사이의 팔로워
                count = random.randint(100, 50000)
            follower_counts.append(count)

        file_path = os.path.join(self.output_dir, '05_insert_follow_relationships.sql')
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write("-- 팔로우 관계 생성\n")
            f.write("SET FOREIGN_KEY_CHECKS = 0;\n")
            f.write("SET UNIQUE_CHECKS = 0;\n")
            f.write("SET AUTOCOMMIT = 0;\n\n")

            relation_id = 1
            batch_values = []

            # 각 스타에 대한 팔로워 할당
            for idx, (star_id, follower_count) in enumerate(zip(self.created_star_ids, follower_counts)):
                star_info_id = idx + 1

                # 팬 중에서 랜덤하게 팔로워 선택
                selected_fans = random.sample(self.created_fan_ids, min(follower_count, len(self.created_fan_ids)))

                for fan_id in selected_fans:
                    value = f"({relation_id}, {fan_id}, {star_info_id})"
                    batch_values.append(value)
                    relation_id += 1

                    # 배치 크기마다 쓰기
                    if len(batch_values) >= self.batch_size:
                        f.write("INSERT INTO star_fan_map (id, follower_id, star_id) VALUES\n")
                        f.write(",\n".join(batch_values))
                        f.write(";\n\n")
                        batch_values = []

                # 스타끼리 팔로우 (각 스타는 10~50명의 다른 스타를 팔로우)
                num_star_follows = random.randint(10, 50)
                other_stars = [s for s in self.created_star_ids if s != star_id]
                followed_stars = random.sample(other_stars, min(num_star_follows, len(other_stars)))

                for followed_star_id in followed_stars:
                    # star_info_id 계산 수정 (인덱스 기반으로 계산)
                    followed_star_info_id = self.created_star_ids.index(followed_star_id) + 1
                    value = f"({relation_id}, {star_id}, {followed_star_info_id})"
                    batch_values.append(value)
                    relation_id += 1

                    if len(batch_values) >= self.batch_size:
                        f.write("INSERT INTO star_fan_map (id, follower_id, star_id) VALUES\n")
                        f.write(",\n".join(batch_values))
                        f.write(";\n\n")
                        batch_values = []

                if (idx + 1) % 100 == 0:
                    print(f"  {idx + 1:,} / {self.star_count:,} 스타의 팔로우 관계 생성됨")

            # 남은 배치 처리
            if batch_values:
                f.write("INSERT INTO star_fan_map (id, follower_id, star_id) VALUES\n")
                f.write(",\n".join(batch_values))
                f.write(";\n\n")

            f.write("COMMIT;\n")
            f.write("SET FOREIGN_KEY_CHECKS = 1;\n")
            f.write("SET UNIQUE_CHECKS = 1;\n")
            f.write("SET AUTOCOMMIT = 1;\n")

    def generate_validation_queries(self):
        """검증 쿼리 생성"""
        print("검증 쿼리 생성 중...")

        file_path = os.path.join(self.output_dir, '06_validation_queries.sql')
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write("-- 데이터 검증 쿼리\n\n")

            # 1. 총 사용자 수 확인
            f.write("-- 1. 총 사용자 수 확인\n")
            f.write("SELECT \n")
            f.write("    COUNT(*) as total_users,\n")
            f.write("    SUM(CASE WHEN role_type = 'F' THEN 1 ELSE 0 END) as fan_count,\n")
            f.write("    SUM(CASE WHEN role_type = 'S' THEN 1 ELSE 0 END) as star_count\n")
            f.write("FROM user;\n\n")

            # 2. star_info 연관관계 확인
            f.write("-- 2. star_info 연관관계 확인\n")
            f.write("SELECT COUNT(*) as star_with_info_count\n")
            f.write("FROM user u\n")
            f.write("JOIN star_info si ON u.star_info_id = si.id\n")
            f.write("WHERE u.role_type = 'S';\n\n")

            # 3. 스타 유형 분포 확인
            f.write("-- 3. 스타 유형 분포 확인\n")
            f.write("SELECT \n")
            f.write("    star_type, \n")
            f.write("    COUNT(*) as count\n")
            f.write("FROM star_type\n")
            f.write("GROUP BY star_type;\n\n")

            # 4. 팔로워 수 분포 확인
            f.write("-- 4. 팔로워 수 분포 확인 (상위 10명)\n")
            f.write("SELECT \n")
            f.write("    si.star_nickname,\n")
            f.write("    COUNT(sfm.follower_id) as follower_count\n")
            f.write("FROM star_info si\n")
            f.write("LEFT JOIN star_fan_map sfm ON si.id = sfm.star_id\n")
            f.write("GROUP BY si.id\n")
            f.write("ORDER BY follower_count DESC\n")
            f.write("LIMIT 10;\n\n")

            # 5. 샘플 데이터 100개 확인
            f.write("-- 5. 샘플 데이터 100개 확인\n")
            f.write("SELECT \n")
            f.write("    u.id,\n")
            f.write("    u.role_type,\n")
            f.write("    u.neo_id,\n")
            f.write("    u.email,\n")
            f.write("    u.neo_nick_name,\n")
            f.write("    si.star_nickname,\n")
            f.write("    GROUP_CONCAT(st.star_type) as star_types\n")
            f.write("FROM user u\n")
            f.write("LEFT JOIN star_info si ON u.star_info_id = si.id\n")
            f.write("LEFT JOIN star_type st ON si.id = st.star_id\n")
            f.write("WHERE u.role_type = 'S'\n")
            f.write("GROUP BY u.id\n")
            f.write("LIMIT 100;\n")

    def generate_all_sql_files(self):
        """모든 SQL 파일 생성"""
        print(f"=== NEO 더미 데이터 생성 시작 (저장 경로: {self.output_dir}) ===")
        self.generate_fans_sql()
        self.generate_star_info_sql()
        self.generate_star_users_sql()
        self.generate_star_types_sql()
        self.generate_follow_relationships_sql()
        self.generate_validation_queries()
        print("=== 모든 SQL 파일 생성 완료 ===")

        # 실행 순서 안내
        print("\n실행 순서:")
        print(f"1. {os.path.join(self.output_dir, '01_insert_fan_users.sql')}")
        print(f"2. {os.path.join(self.output_dir, '02_insert_star_info.sql')}")
        print(f"3. {os.path.join(self.output_dir, '03_insert_star_users.sql')}")
        print(f"4. {os.path.join(self.output_dir, '04_insert_star_types.sql')}")
        print(f"5. {os.path.join(self.output_dir, '05_insert_follow_relationships.sql')}")
        print(f"6. {os.path.join(self.output_dir, '06_validation_queries.sql')} (검증용)")


# 실행
if __name__ == "__main__":
    generator = NEODummyDataGenerator()
    generator.generate_all_sql_files()