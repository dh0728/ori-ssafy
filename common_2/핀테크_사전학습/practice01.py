import OpenDartReader
import FinanceDataReader as fdr

api_key=''

dart = OpenDartReader(api_key)

# 1. KRX 상장 기업 중 한가지 종목 선정
# 2.원하는 조회 기간 (조회 시작일 ~ 조회 종료일) 설정
name = '삼성전자'
start = '2022-01-01'
end = '2025-03-07'

# 선택한 종목의 조회 기간 중 가장 **최신 사업보고서** 찾아 텍스트 파일(.txt)로 저장
dart_list = dart.list(name, start, end, final=False)

report_idx = dart_list['rcept_no'][0]

xml_text = dart.document(report_idx)



# with open(f'{name}.txt', 'w', encoding='utf-8') as f:
#     f.write(xml_text)

# 선택한 종목의 조회 기간에 해당하는 주가 데이터를 캔들차트 그리기

