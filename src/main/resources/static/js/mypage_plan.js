// 국가 리스트 동적 로딩
// async function loadCountryOptions() {
//   try {
//     const response = await fetch('/api/countries', { credentials: 'same-origin' });
//     if (!response.ok) throw new Error('국가 목록 조회 실패');
//     const countries = await response.json();
//
//     const select = document.getElementById('planCountry');
//     select.innerHTML = '<option value="">여행지 선택</option>'; // 초기화
//
//     countries.forEach(country => {
//       select.innerHTML += `<option value="${country.countryName}">${country.flagEmoji || ''} ${country.countryName}</option>`;
//     });
//   } catch (err) {
//     alert(err.message);
//   }
// }

function getCountries() {
    fetch("/api/mypage/countries", {
        method: "GET"
    })
        .then(res => {
            if (!res.ok) throw new Error("Failed to fetch COUNTRY LIST");
            return res.json();
        })
        .then(countries => {
            const select = document.getElementById('planCountry');
            select.innerHTML = '<option value="">여행지 선택</option>';

            countries.forEach(country => {
                select.innerHTML += `<option value="${country.country_name}">${country.country_name}</option>`;
            });
        })
        .catch(err => {
            console.error(err);
            alert("서버 오류");
        });
}

// 플랜 목록 불러오기
function loadPlans() {
    fetch("/users/current/wishlists", {
        method: "GET",
        credentials: "same-origin"
    })
        .then(res => {
            if (!res.ok) throw new Error("Failed to fetch WISHLISTS");
            return res.json();
        })
        .then(resData => {
            const plans = resData.data;
            const planCount = document.getElementById("planCount");
            planCount.textContent = plans.length;

            let html = "";
            plans.forEach(plan => {
                html += `<tr>
          <td><img src="${plan.flagUrl}" alt="flag" style="width:20px; height:14px; margin-right:5px;"> ${plan.countryName}</td>
          <td>${plan.travelDate}</td>
          <td>${plan.endDate}</td>
          <td>
            <button class="btn btn-sm btn-outline-danger" onclick="deletePlan(${plan.wishlistId})">삭제</button>
          </td>
        </tr>`;
            });

            document.getElementById("planListBody").innerHTML = html;
        })
        .catch(err => {
            console.error(err);
            alert("로그인 필요 또는 서버 오류");
            document.getElementById("planCount").textContent = "0";
        });
}

// 플랜 추가
document.getElementById("addPlanForm").onsubmit = function(e) {
  e.preventDefault();
  const countryName = document.getElementById("planCountry").value;
  const travelDate = document.getElementById("planStart").value;
  const endDate = document.getElementById("planEnd").value;

  if (!countryName || !travelDate || !endDate) {
    alert("모든 항목을 입력하세요.");
    return;
  }

  // 날짜 검증
  const startDateObj = new Date(travelDate + 'T00:00:00');
  const endDateObj = new Date(endDate + 'T00:00:00');
  const today = new Date();
  today.setHours(0, 0, 0, 0);

  console.log('Start Date:', startDateObj);
  console.log('End Date:', endDateObj);
  console.log('Today:', today);

  if (startDateObj > endDateObj) {
    alert('여행 시작일은 종료일보다 이전이어야 합니다.');
    return;
  }

  if (startDateObj < today) {
    alert('여행 시작일은 오늘 이후여야 합니다.');
    return;
  }

  fetch("/api/mypage/plan", {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    credentials: "same-origin",
    body: JSON.stringify({
      countryName: countryName,
      travelDate: travelDate,
      endDate: endDate
    })
  })
    .then(res => {
      if (!res.ok) throw new Error("플랜 추가 실패");
      return res.json();
    })
    .then(data => {
      alert(data.message || "일정이 추가되었습니다.");
      loadPlans();
      document.getElementById("addPlanForm").reset();
    })
    .catch(error => {
      console.error("플랜 추가 실패:", error);
      alert("일정 추가에 실패했습니다.");
    });
};

// 플랜 삭제
function deletePlan(planId) {
  if (!confirm("정말 삭제하시겠습니까?")) return;
  fetch(`/api/mypage/plan/${planId}`, {
    method: "DELETE",
    credentials: "same-origin"
  })
    .then(res => res.json())
    .then(data => {
      alert(data.message);
      loadPlans();
    })
    .catch(() => alert("삭제 실패"));
}

// 모달이 열릴 때마다 플랜 목록과 국가 리스트 새로고침
document.getElementById('planModal').addEventListener('show.bs.modal', () => {
  loadPlans();
  // loadCountryOptions();
});

document.addEventListener("DOMContentLoaded", () => {
    getCountries();
    loadPlans();
    const chkWL = document.getElementById('chkWL');
    if (chkWL) {
        chkWL.addEventListener('click', function (e) {
            console.log("CLICKED");
            loadPlans();
        });
    }
});