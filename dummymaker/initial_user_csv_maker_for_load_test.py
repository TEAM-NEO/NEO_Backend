import random
import string
import csv
from datetime import datetime, timedelta
import os


class NEODummyDataCSVGenerator:
    def __init__(self, seed=42):
        # 재현 가능한 데이터를 위한 시드 설정
        random.seed(seed)

        self.fan_count = 2_000_000
        self.star_count = 10_000
        self.batch_size = 10_000
        self.output_dir = "csv_output"  # CSV 파일 저장 디렉터리

        # 스타 유형 (NEOStarTypeEntity 참고)
        self.star_types = ['YOUTUBER', 'SINGER', 'ACTOR', 'SNS_INFLUENCER']

        # 성별 코드 (NEOGenderType 참고)
        self.genders = ['M', 'F']

        # 소셜 로그인 타입 (NEOOAuth2ProviderType 참고)
        self.provider_types = ['kakao', 'naver', 'google', 'NULL']

        # 생성된 데이터 추적
        self.created_fan_ids = []
        self.created_star_ids = []
        self.created_star_info_ids = []

        # Unique 제약조건을 위한 추적 집합들
        self.used_phone_numbers = set()
        self.used_user_names = set()

        # 출력 디렉터리 생성
        self._ensure_output_directory()

    def _ensure_output_directory(self):
        """CSV 파일을 저장할 디렉터리가 없으면 생성"""
        if not os.path.exists(self.output_dir):
            os.makedirs(self.output_dir)
            print(f"디렉터리 생성됨: {self.output_dir}")

    def generate_random_string(self, length=10, seed_suffix=0):
        """재현 가능한 랜덤 문자열 생성"""
        # 각 호출마다 고유한 시드를 사용하되, 전체적으로는 재현 가능하도록
        local_random = random.Random(random.getstate()[1][0] + seed_suffix)
        return ''.join(local_random.choices(string.ascii_lowercase + string.digits, k=length))

    def generate_unique_phone_number(self, user_index):
        """
        유니크한 핸드폰 번호 생성 (총 201만개가 모두 다르도록 보장)
        010-0000-0001부터 순차적으로 증가
        """
        # user_index는 1부터 시작하는 전체 사용자 인덱스
        # 010-0000-0001부터 시작
        phone_number = user_index

        # 뒤 8자리를 분할: 앞 4자리, 뒤 4자리
        back_4 = phone_number % 10000
        front_4 = (phone_number // 10000) % 10000

        # 010-XXXX-YYYY 형태로 생성
        formatted_phone = f"010-{front_4:04d}-{back_4:04d}"

        # 중복 체크 (안전장치)
        if formatted_phone in self.used_phone_numbers:
            # 만약 중복이면 다음 번호 사용
            return self.generate_unique_phone_number(user_index + 1)

        self.used_phone_numbers.add(formatted_phone)
        return formatted_phone

    def generate_unique_korean_name(self, user_index, user_type="fan"):
        """
        유니크한 한국어 이름 생성 (UNIQUE_ID_EMAIL_NAME_IDX 제약조건 보장)
        """
        last_names = ['김', '이', '박', '최', '정', '강', '조', '윤', '장', '임']
        first_names = ['민수', '지은', '서준', '서연', '하준', '지우', '지훈', '수빈', '예준', '서윤']

        # 기본 이름 조합 생성
        local_random = random.Random(user_index)
        base_name = local_random.choice(last_names) + local_random.choice(first_names)

        # 유니크하게 만들기 위해 사용자 타입과 인덱스 추가
        unique_name = f"{base_name}{user_type}{user_index}"

        # 중복 체크
        counter = 0
        final_name = unique_name
        while final_name in self.used_user_names:
            counter += 1
            final_name = f"{unique_name}_{counter}"

        self.used_user_names.add(final_name)
        return final_name

    def generate_timestamp(self, seed_suffix=0):
        """재현 가능한 타임스탬프 생성"""
        base_date = datetime(2023, 1, 1)
        local_random = random.Random(random.getstate()[1][0] + seed_suffix)
        random_days = local_random.randint(0, 365)
        return (base_date + timedelta(days=random_days)).strftime('%Y-%m-%d %H:%M:%S')

    def generate_fans_csv(self):
        """팬 계정 200만개 생성 CSV"""
        print("팬 계정 200만개 CSV 생성 중...")

        file_path = os.path.join(self.output_dir, 'fan_users.csv')
        with open(file_path, 'w', newline='', encoding='utf-8') as csvfile:
            fieldnames = [
                'id', 'role_type', 'neo_id', 'social_id', 'user_name',
                'password', 'email', 'phone_number', 'provider_type',
                'neo_nick_name', 'gender', 'star_info_id', 'created_at', 'updated_at'
            ]
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

            # 헤더 작성
            writer.writeheader()

            # 배치 단위로 데이터 생성
            for batch_start in range(0, self.fan_count, self.batch_size):
                batch_end = min(batch_start + self.batch_size, self.fan_count)
                batch_data = []

                for i in range(batch_start, batch_end):
                    fan_id = i + 1
                    self.created_fan_ids.append(fan_id)

                    neo_id = f"fan_{fan_id}"
                    email = f"fan{fan_id}@example.com"
                    password = self.generate_random_string(20, fan_id)
                    # 유니크한 핸드폰 번호 생성 (전체 사용자 인덱스 사용)
                    phone = self.generate_unique_phone_number(fan_id)
                    nickname = f"팬_{fan_id}"
                    # 유니크한 사용자 이름 생성
                    username = self.generate_unique_korean_name(fan_id, "팬")

                    # 재현 가능한 랜덤 선택
                    local_random = random.Random(fan_id)
                    gender = local_random.choice(self.genders)
                    provider = local_random.choice(self.provider_types)
                    social_id = f"social_{fan_id}" if provider else None
                    created_at = self.generate_timestamp(fan_id)

                    row_data = {
                        'id': fan_id,
                        'role_type': 'F',
                        'neo_id': neo_id,
                        'social_id': social_id if social_id else '',
                        'user_name': username,
                        'password': password,
                        'email': email,
                        'phone_number': phone,
                        'provider_type': provider if provider else '',
                        'neo_nick_name': nickname,
                        'gender': gender,
                        'star_info_id': 'NULL',  # 팬은 star_info_id가 NULL
                        'created_at': created_at,
                        'updated_at': created_at
                    }
                    batch_data.append(row_data)

                # 배치 데이터를 CSV에 작성
                writer.writerows(batch_data)

                if (batch_end % 100000) == 0:
                    print(f"  {batch_end:,} / {self.fan_count:,} 팬 계정 생성됨")

    def generate_star_info_csv(self):
        """star_info 10,000개 생성 CSV"""
        print("star_info 10,000개 CSV 생성 중...")

        file_path = os.path.join(self.output_dir, 'star_info.csv')
        with open(file_path, 'w', newline='', encoding='utf-8') as csvfile:
            fieldnames = ['id', 'star_nickname']
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

            # 헤더 작성
            writer.writeheader()

            # 데이터 생성
            for i in range(self.star_count):
                star_info_id = i + 1
                self.created_star_info_ids.append(star_info_id)

                row_data = {
                    'id': star_info_id,
                    'star_nickname': f"스타_{star_info_id}"
                }
                writer.writerow(row_data)

    def generate_star_users_csv(self):
        """스타 계정 10,000개 생성 CSV (star_info와 1:1 매핑)"""
        print("스타 계정 10,000개 CSV 생성 중...")

        file_path = os.path.join(self.output_dir, 'star_users.csv')
        with open(file_path, 'w', newline='', encoding='utf-8') as csvfile:
            fieldnames = [
                'id', 'role_type', 'neo_id', 'social_id', 'user_name',
                'password', 'email', 'phone_number', 'provider_type',
                'neo_nick_name', 'gender', 'star_info_id', 'created_at', 'updated_at'
            ]
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

            # 헤더 작성
            writer.writeheader()

            # 팬 계정 이후의 ID부터 시작
            star_id_start = self.fan_count + 1

            # 데이터 생성
            for i in range(self.star_count):
                star_id = star_id_start + i
                star_info_id = i + 1  # star_info의 ID와 매칭
                self.created_star_ids.append(star_id)

                neo_id = f"star_{i + 1}"
                email = f"star{i + 1}@example.com"
                password = self.generate_random_string(20, star_id)
                # 유니크한 핸드폰 번호 생성 (전체 사용자 인덱스: 팬 다음부터)
                phone = self.generate_unique_phone_number(star_id)
                nickname = f"스타_{i + 1}"
                # 유니크한 사용자 이름 생성
                username = self.generate_unique_korean_name(star_id, "스타")

                # 재현 가능한 랜덤 선택
                local_random = random.Random(star_id)
                gender = local_random.choice(self.genders)
                provider = local_random.choice(self.provider_types)
                social_id = f"star_social_{i + 1}" if provider else 'NULL'
                created_at = self.generate_timestamp(star_id)

                row_data = {
                    'id': star_id,
                    'role_type': 'S',
                    'neo_id': neo_id,
                    'social_id': social_id if social_id else '',
                    'user_name': username,
                    'password': password,
                    'email': email,
                    'phone_number': phone,
                    'provider_type': provider if provider else '',
                    'neo_nick_name': nickname,
                    'gender': gender,
                    'star_info_id': star_info_id,
                    'created_at': created_at,
                    'updated_at': created_at
                }
                writer.writerow(row_data)

    def generate_star_types_csv(self):
        """스타 유형 데이터 생성 CSV"""
        print("스타 유형 데이터 CSV 생성 중...")

        file_path = os.path.join(self.output_dir, 'star_types.csv')
        with open(file_path, 'w', newline='', encoding='utf-8') as csvfile:
            fieldnames = ['id', 'star_id', 'star_type']
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

            # 헤더 작성
            writer.writeheader()

            type_id = 1

            for star_info_id in self.created_star_info_ids:
                # 재현 가능한 랜덤 선택
                local_random = random.Random(star_info_id)

                # 각 스타는 1~3개의 유형을 가짐
                num_types = local_random.randint(1, 3)
                selected_types = local_random.sample(self.star_types, num_types)

                for star_type in selected_types:
                    # NEOStarDetailClassification의 코드 매핑
                    type_code_map = {
                        'YOUTUBER': 'YO',
                        'SINGER': 'SI',
                        'ACTOR': 'AC',
                        'SNS_INFLUENCER': 'SN'
                    }

                    row_data = {
                        'id': type_id,
                        'star_id': star_info_id,
                        'star_type': type_code_map[star_type]
                    }
                    writer.writerow(row_data)
                    type_id += 1

    def generate_follow_relationships_csv(self):
        """팔로우 관계 생성 CSV"""
        print("팔로우 관계 CSV 생성 중...")

        # 팔로워 수 분포 생성 (상위 50%는 약 10만명) - 재현 가능하게
        follower_counts = []
        for i in range(self.star_count):
            local_random = random.Random(i + 10000)  # 스타 ID와 다른 시드 사용
            if i < self.star_count // 2:  # 상위 50%
                # 8만 ~ 12만 사이의 팔로워
                count = local_random.randint(80000, 120000)
            else:  # 하위 50%
                # 100 ~ 50000 사이의 팔로워
                count = local_random.randint(100, 50000)
            follower_counts.append(count)

        file_path = os.path.join(self.output_dir, 'follow_relationships.csv')
        with open(file_path, 'w', newline='', encoding='utf-8') as csvfile:
            fieldnames = ['id', 'follower_id', 'star_id']
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

            # 헤더 작성
            writer.writeheader()

            relation_id = 1
            batch_data = []

            # 각 스타에 대한 팔로워 할당
            for idx, (star_id, follower_count) in enumerate(zip(self.created_star_ids, follower_counts)):
                star_info_id = idx + 1

                # 재현 가능한 팔로워 선택
                local_random = random.Random(star_id + 20000)  # 다른 시드 사용

                # 팬 중에서 랜덤하게 팔로워 선택
                selected_fans = local_random.sample(self.created_fan_ids,
                                                    min(follower_count, len(self.created_fan_ids)))

                for fan_id in selected_fans:
                    row_data = {
                        'id': relation_id,
                        'follower_id': fan_id,
                        'star_id': star_info_id
                    }
                    batch_data.append(row_data)
                    relation_id += 1

                    # 배치 크기마다 쓰기
                    if len(batch_data) >= self.batch_size:
                        writer.writerows(batch_data)
                        batch_data = []

                # 스타끼리 팔로우 (각 스타는 10~50명의 다른 스타를 팔로우)
                star_follow_random = random.Random(star_id + 30000)  # 또 다른 시드 사용
                num_star_follows = star_follow_random.randint(10, 50)
                other_stars = [s for s in self.created_star_ids if s != star_id]
                followed_stars = star_follow_random.sample(other_stars, min(num_star_follows, len(other_stars)))

                for followed_star_id in followed_stars:
                    # star_info_id 계산 수정 (인덱스 기반으로 계산)
                    followed_star_info_id = self.created_star_ids.index(followed_star_id) + 1
                    row_data = {
                        'id': relation_id,
                        'follower_id': star_id,
                        'star_id': followed_star_info_id
                    }
                    batch_data.append(row_data)
                    relation_id += 1

                    if len(batch_data) >= self.batch_size:
                        writer.writerows(batch_data)
                        batch_data = []

                if (idx + 1) % 100 == 0:
                    print(f"  {idx + 1:,} / {self.star_count:,} 스타의 팔로우 관계 생성됨")

            # 남은 배치 처리
            if batch_data:
                writer.writerows(batch_data)

    def generate_data_summary(self):
        """데이터 요약 정보 생성"""
        print("데이터 요약 정보 생성 중...")

        file_path = os.path.join(self.output_dir, 'data_summary.txt')
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write("NEO 프로젝트 더미 데이터 요약\n")
            f.write("=" * 50 + "\n\n")

            f.write(f"생성 일시: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}\n")
            f.write(f"랜덤 시드: 42 (재현 가능)\n\n")

            f.write("생성된 파일 목록:\n")
            f.write("1. fan_users.csv - 팬 사용자 데이터 (2,000,000건)\n")
            f.write("2. star_info.csv - 스타 정보 데이터 (10,000건)\n")
            f.write("3. star_users.csv - 스타 사용자 데이터 (10,000건)\n")
            f.write("4. star_types.csv - 스타 유형 데이터 (약 15,000~30,000건)\n")
            f.write("5. follow_relationships.csv - 팔로우 관계 데이터 (약 500,000,000건)\n\n")

            f.write("데이터 특징:\n")
            f.write("- 팬 계정: 200만개 (role_type='F')\n")
            f.write("- 스타 계정: 1만개 (role_type='S')\n")
            f.write("- 스타 유형: YOUTUBER, SINGER, ACTOR, SNS_INFLUENCER\n")
            f.write("- 상위 50% 스타: 8~12만 팔로워\n")
            f.write("- 하위 50% 스타: 100~5만 팔로워\n")
            f.write("- 각 스타는 1~3개의 유형을 가짐\n")
            f.write("- 스타끼리도 팔로우 관계 형성 (10~50명)\n")
            f.write("- 동일한 시드(42)로 실행하면 항상 같은 데이터 생성\n\n")

            f.write("Unique 제약조건 보장:\n")
            f.write("- phone_number: 모든 201만 사용자가 서로 다른 핸드폰 번호 (010-0000-0001~)\n")
            f.write("- neo_nick_name: 모든 사용자가 서로 다른 닉네임\n")
            f.write("- neo_id: 모든 사용자가 서로 다른 ID\n")
            f.write("- email: 모든 사용자가 서로 다른 이메일\n")
            f.write("- user_name: 모든 사용자가 서로 다른 이름 (UNIQUE_ID_EMAIL_NAME_IDX 보장)\n\n")

            f.write("MySQL LOAD DATA 예시:\n")
            f.write("LOAD DATA INFILE 'fan_users.csv'\n")
            f.write("INTO TABLE user\n")
            f.write("FIELDS TERMINATED BY ','\n")
            f.write("ENCLOSED BY '\"'\n")
            f.write("LINES TERMINATED BY '\\n'\n")
            f.write("IGNORE 1 ROWS;\n")

    def generate_sample_data(self):
        """샘플 데이터 생성 (검증용)"""
        print("샘플 데이터 생성 중...")

        file_path = os.path.join(self.output_dir, 'sample_data.csv')
        with open(file_path, 'w', newline='', encoding='utf-8') as csvfile:
            fieldnames = [
                'user_id', 'role_type', 'neo_id', 'email', 'neo_nick_name',
                'star_nickname', 'star_types', 'follower_count'
            ]
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

            # 헤더 작성
            writer.writeheader()

            # 스타 샘플 100개 생성 (재현 가능하게)
            sample_random = random.Random(50000)  # 샘플용 시드
            sample_stars = sample_random.sample(range(1, min(101, len(self.created_star_ids) + 1)),
                                                min(100, len(self.created_star_ids)))

            for star_info_id in sample_stars:
                star_id = self.fan_count + star_info_id

                # 재현 가능한 랜덤 선택
                local_random = random.Random(star_info_id)

                # 스타 유형 랜덤 생성 (실제와 동일한 로직)
                num_types = local_random.randint(1, 3)
                selected_types = local_random.sample(self.star_types, num_types)

                # 팔로워 수 계산 (실제와 동일한 로직)
                follower_random = random.Random(star_info_id + 10000)
                if star_info_id <= self.star_count // 2:
                    follower_count = follower_random.randint(80000, 120000)
                else:
                    follower_count = follower_random.randint(100, 50000)

                row_data = {
                    'user_id': star_id,
                    'role_type': 'S',
                    'neo_id': f"star_{star_info_id}",
                    'email': f"star{star_info_id}@example.com",
                    'neo_nick_name': f"스타닉네임_{star_info_id}",
                    'star_nickname': f"스타_{star_info_id}",
                    'star_types': ','.join(selected_types),
                    'follower_count': follower_count
                }
                writer.writerow(row_data)

    def generate_all_csv_files(self):
        """모든 CSV 파일 생성"""
        print(f"=== NEO 더미 데이터 CSV 생성 시작 (저장 경로: {self.output_dir}) ===")
        print(f"랜덤 시드: 42 (재현 가능한 데이터 생성)")
        print(f"Unique 제약조건 보장: phone_number, neo_nick_name, neo_id, email, user_name")

        # 1. 팬 계정 생성
        self.generate_fans_csv()

        # 2. 스타 정보 생성
        self.generate_star_info_csv()

        # 3. 스타 계정 생성
        self.generate_star_users_csv()

        # 4. 스타 유형 생성
        self.generate_star_types_csv()

        # 5. 팔로우 관계 생성
        self.generate_follow_relationships_csv()

        # 6. 데이터 요약 생성
        self.generate_data_summary()

        # 7. 샘플 데이터 생성
        self.generate_sample_data()

        print("=== 모든 CSV 파일 생성 완료 ===")

        # 생성된 파일 목록 출력
        print(f"\n생성된 파일 목록 ({self.output_dir}):")
        files = [
            'fan_users.csv',
            'star_info.csv',
            'star_users.csv',
            'star_types.csv',
            'follow_relationships.csv',
            'data_summary.txt',
            'sample_data.csv'
        ]

        for i, file in enumerate(files, 1):
            file_path = os.path.join(self.output_dir, file)
            if os.path.exists(file_path):
                file_size = os.path.getsize(file_path) / (1024 * 1024)  # MB 단위
                print(f"{i}. {file} ({file_size:.1f} MB)")

        print(f"\nMySQL 데이터 로드 순서:")
        print("1. fan_users.csv → user 테이블")
        print("2. star_info.csv → star_info 테이블")
        print("3. star_users.csv → user 테이블")
        print("4. star_types.csv → star_type 테이블")
        print("5. follow_relationships.csv → star_fan_map 테이블")

        print(f"\nUnique 제약조건 보장:")
        print(f"- 총 {len(self.used_phone_numbers):,}개 핸드폰 번호 생성 (모두 고유)")
        print(f"- 총 {len(self.used_user_names):,}개 사용자 이름 생성 (모두 고유)")
        print(f"- NEO Backend의 unique 제약조건 완전 준수")

        print(f"\n참고: 동일한 시드(42)로 실행하면 항상 같은 데이터가 생성됩니다.")


# 실행
if __name__ == "__main__":
    # 시드를 42로 고정하여 재현 가능한 데이터 생성
    # 다른 시드값을 원하면 NEODummyDataCSVGenerator(seed=다른값) 으로 변경
    generator = NEODummyDataCSVGenerator(seed=42)
    generator.generate_all_csv_files()