document.addEventListener('DOMContentLoaded', function () {
    const features = document.querySelectorAll('.features');
    const contentWrapper = document.querySelector('.featurecontent');
    const fourFeaturesWrapper = document.querySelector('.fourfeatures');  // 确保这个选择器正确
    let currentIndex = 0;

    function updateContentPosition() {
        const offset = currentIndex * 1000; // 每个内容的宽度是1000px
        fourFeaturesWrapper.style.transform = `translateX(-${offset}px)`;
    }

    features.forEach((feature, index) => {
        feature.addEventListener('click', () => {
            currentIndex = index;
            updateContentPosition();
        });
    });

    document.querySelector('.button-arrow.left').addEventListener('click', () => {
        if (currentIndex > 0) {
            currentIndex--;
            updateContentPosition();
        }
    });

    document.querySelector('.button-arrow.right').addEventListener('click', () => {
        if (currentIndex < features.length - 1) {
            currentIndex++;
            updateContentPosition();
        }
    });
});
