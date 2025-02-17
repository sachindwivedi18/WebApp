<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Collapsible Headers with API Data</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        h2 {
            margin-bottom: 20px;
        }

        .buttons-container {
            margin-bottom: 20px;
        }

        button {
            padding: 10px 20px;
            margin-right: 10px;
            background-color: DodgerBlue;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

            button:hover {
                background-color: #1e90ff;
            }

        textarea {
            width: 100%;
            height: 80px;
            margin-top: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 8px;
            font-size: 14px;
            resize: none;
        }

        details {
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: white;
            overflow: hidden;
            padding: 5px;
        }

        summary {
            font-weight: bold;
            cursor: pointer;
            background-color: DodgerBlue;
            color: white;
            padding: 10px;
            border-radius: 5px;
            outline: none;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .subsection {
            margin-top: 10px;
        }

        label {
            font-weight: bold;
            display: block;
            margin-bottom: 5px;
        }

        .subsection-buttons {
            margin-top: 10px;
        }

            .subsection-buttons button {
                margin-right: 5px;
            }

        .status-labels {
            margin-top: 10px;
        }

        .checkbox-container {
            margin-right: 10px;
        }

        .gallery {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
        }

            .gallery img {
                max-width: 100px;
                max-height: 100px;
                cursor: pointer;
            }
        /* Lightbox Styles */
        .lightbox {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.8);
            justify-content: center;
            align-items: center;
            z-index: 9999;
        }

            .lightbox img {
                max-width: 90%;
                max-height: 90%;
            }

            .lightbox .close {
                position: absolute;
                top: 20px;
                right: 20px;
                font-size: 30px;
                color: white;
                cursor: pointer;
                background-color: transparent;
                border: none;
            }
    </style>
</head>
<body>

    <h2>Collapsible Sections with API Data</h2>

    <!-- Buttons for Execute Suite and Add New Test Case -->
    <div class="buttons-container">
        <button id="executeSuiteBtn">Execute Suite</button>
        <button id="addNewTestCaseBtn">Add New Test Case</button>
    </div>

    <!-- Textarea below buttons -->
    <textarea id="newTestCaseTitle" placeholder="Enter new test case title here..."></textarea>

    <div id="content"></div>

    <!-- Lightbox for displaying images -->
    <div id="lightbox" class="lightbox">
        <button class="close" id="closeLightbox">&times;</button>
        <img id="lightboxImage" src="" alt="Full Image" />
    </div>

    <script>
        async function fetchData() {
            try {
                const response = await fetch('https://jsonplaceholder.typicode.com/posts?_limit=5');
                const data = await response.json();

                const container = document.getElementById('content');
                container.innerHTML = '';

                data.forEach(item => {
                    const details = document.createElement('details');

                    const summary = document.createElement('summary');
                    summary.innerHTML = `
                            <div style="display: flex; align-items: center;">
                                <input type="checkbox" class="checkbox-container" />
                                TestCase ID: ${item.id} - ${item.title}
                            </div>
                        `;

                    // Precondition Section
                    const preconditionSection = document.createElement('div');
                    preconditionSection.classList.add('subsection');

                    const preconditionLabel = document.createElement('label');
                    preconditionLabel.textContent = 'Precondition';

                    const preconditionTextArea = document.createElement('textarea');
                    preconditionTextArea.placeholder = 'Enter precondition here...';

                    preconditionSection.appendChild(preconditionLabel);
                    preconditionSection.appendChild(preconditionTextArea);

                    // Test Steps Section
                    const testStepsSection = document.createElement('div');
                    testStepsSection.classList.add('subsection');

                    const testStepsLabel = document.createElement('label');
                    testStepsLabel.textContent = 'Test Steps';

                    const testStepsTextArea = document.createElement('textarea');
                    testStepsTextArea.placeholder = 'Enter test steps here...';

                    testStepsSection.appendChild(testStepsLabel);
                    testStepsSection.appendChild(testStepsTextArea);

                    // Actual Result Section
                    const actualResultSection = document.createElement('div');
                    actualResultSection.classList.add('subsection');

                    const actualResultLabel = document.createElement('label');
                    actualResultLabel.textContent = 'Actual Result';

                    const actualResultTextArea = document.createElement('textarea');
                    actualResultTextArea.placeholder = 'Enter actual result here...';

                    actualResultSection.appendChild(actualResultLabel);
                    actualResultSection.appendChild(actualResultTextArea);

                    // Expected Result Section
                    const expectedResultSection = document.createElement('div');
                    expectedResultSection.classList.add('subsection');

                    const expectedResultLabel = document.createElement('label');
                    expectedResultLabel.textContent = 'Expected Result';

                    const expectedResultTextArea = document.createElement('textarea');
                    expectedResultTextArea.placeholder = 'Enter expected result here...';

                    expectedResultSection.appendChild(expectedResultLabel);
                    expectedResultSection.appendChild(expectedResultTextArea);

                    // User Journey Section (Upload Images)
                    const userJourneySection = document.createElement('div');
                    userJourneySection.classList.add('subsection');

                    const userJourneyLabel = document.createElement('label');
                    userJourneyLabel.textContent = 'User Journey (Upload Images)';

                    const uploadImagesButton = document.createElement('input');
                    uploadImagesButton.type = 'file';
                    uploadImagesButton.accept = 'image/*';
                    uploadImagesButton.multiple = true;

                    const galleryContainer = document.createElement('div');
                    galleryContainer.classList.add('gallery');

                    uploadImagesButton.addEventListener('change', function (event) {
                        const files = event.target.files;
                        galleryContainer.innerHTML = '';

                        Array.from(files).forEach(file => {
                            const img = document.createElement('img');
                            const reader = new FileReader();

                            reader.onload = function (e) {
                                img.src = e.target.result;
                                img.onclick = function () {
                                    showLightbox(e.target.result);
                                };
                                galleryContainer.appendChild(img);
                            };
                            reader.readAsDataURL(file);
                        });
                    });

                    userJourneySection.appendChild(userJourneyLabel);
                    userJourneySection.appendChild(uploadImagesButton);
                    userJourneySection.appendChild(galleryContainer);

                    // Status Labels: Last Executed On & Automation Status
                    const statusSection = document.createElement('div');
                    statusSection.classList.add('status-labels');

                    const lastExecutedOnLabel = document.createElement('label');
                    lastExecutedOnLabel.textContent = 'Last Executed On: Not executed yet';

                    const automationStatusLabel = document.createElement('label');
                    automationStatusLabel.textContent = 'Automation Status: Pending';

                    statusSection.appendChild(lastExecutedOnLabel);
                    statusSection.appendChild(automationStatusLabel);

                    // Execute and Update Buttons
                    const buttonSection = document.createElement('div');
                    buttonSection.classList.add('subsection-buttons');

                    const executeButton = document.createElement('button');
                    executeButton.textContent = 'Execute';
                    executeButton.onclick = function () {
                        alert(`Executing: ${item.title}`);
                    };

                    const updateButton = document.createElement('button');
                    updateButton.textContent = 'Update';
                    updateButton.onclick = function () {
                        alert(`Updating: ${item.title}`);
                    };

                    buttonSection.appendChild(executeButton);
                    buttonSection.appendChild(updateButton);

                    details.appendChild(summary);
                    details.appendChild(preconditionSection);
                    details.appendChild(testStepsSection);
                    details.appendChild(actualResultSection);
                    details.appendChild(expectedResultSection);
                    details.appendChild(userJourneySection);
                    details.appendChild(statusSection);
                    details.appendChild(buttonSection);
                    container.appendChild(details);
                });
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        }

        // Function to show lightbox with the selected image
        function showLightbox(imageSrc) {
            const lightbox = document.getElementById('lightbox');
            const lightboxImage = document.getElementById('lightboxImage');
            lightbox.style.display = 'flex';
            lightboxImage.src = imageSrc;

            // Navigate through images using keyboard arrows
            let images = document.querySelectorAll('.gallery img');
            let currentIndex = Array.from(images).findIndex(img => img.src === imageSrc);

            document.addEventListener('keydown', function (event) {
                if (event.key === 'ArrowLeft') {
                    currentIndex = (currentIndex > 0) ? currentIndex - 1 : images.length - 1;
                    lightboxImage.src = images[currentIndex].src;
                } else if (event.key === 'ArrowRight') {
                    currentIndex = (currentIndex < images.length - 1) ? currentIndex + 1 : 0;
                    lightboxImage.src = images[currentIndex].src;
                }
            });
        }

        // Function to close lightbox
        document.getElementById('closeLightbox').addEventListener('click', function () {
            document.getElementById('lightbox').style.display = 'none';
        });

        // Function to add a new test case
        function addNewTestCase() {
            const newTestCaseTitle = document.getElementById('newTestCaseTitle').value.trim();

            if (newTestCaseTitle === "") {
                alert("Please enter a title for the new test case.");
                return;
            }

            const container = document.getElementById('content');

            const details = document.createElement('details');

            const summary = document.createElement('summary');
            summary.innerHTML = `
                    <div style="display: flex; align-items: center;">
                        <input type="checkbox" class="checkbox-container" />
                        TestCase ID: New - ${newTestCaseTitle}
                    </div>
                `;

            // Precondition Section
            const preconditionSection = document.createElement('div');
            preconditionSection.classList.add('subsection');

            const preconditionLabel = document.createElement('label');
            preconditionLabel.textContent = 'Precondition';

            const preconditionTextArea = document.createElement('textarea');
            preconditionTextArea.placeholder = 'Enter precondition here...';

            preconditionSection.appendChild(preconditionLabel);
            preconditionSection.appendChild(preconditionTextArea);

            // Test Steps Section
            const testStepsSection = document.createElement('div');
            testStepsSection.classList.add('subsection');

            const testStepsLabel = document.createElement('label');
            testStepsLabel.textContent = 'Test Steps';

            const testStepsTextArea = document.createElement('textarea');
            testStepsTextArea.placeholder = 'Enter test steps here...';

            testStepsSection.appendChild(testStepsLabel);
            testStepsSection.appendChild(testStepsTextArea);

            // Actual Result Section
            const actualResultSection = document.createElement('div');
            actualResultSection.classList.add('subsection');

            const actualResultLabel = document.createElement('label');
            actualResultLabel.textContent = 'Actual Result';

            const actualResultTextArea = document.createElement('textarea');
            actualResultTextArea.placeholder = 'Enter actual result here...';

            actualResultSection.appendChild(actualResultLabel);
            actualResultSection.appendChild(actualResultTextArea);

            // Expected Result Section
            const expectedResultSection = document.createElement('div');
            expectedResultSection.classList.add('subsection');

            const expectedResultLabel = document.createElement('label');
            expectedResultLabel.textContent = 'Expected Result';

            const expectedResultTextArea = document.createElement('textarea');
            expectedResultTextArea.placeholder = 'Enter expected result here...';

            expectedResultSection.appendChild(expectedResultLabel);
            expectedResultSection.appendChild(expectedResultTextArea);

            // User Journey Section (Upload Images)
            const userJourneySection = document.createElement('div');
            userJourneySection.classList.add('subsection');

            const userJourneyLabel = document.createElement('label');
            userJourneyLabel.textContent = 'User Journey (Upload Images)';

            const uploadImagesButton = document.createElement('input');
            uploadImagesButton.type = 'file';
            uploadImagesButton.accept = 'image/*';
            uploadImagesButton.multiple = true;

            const galleryContainer = document.createElement('div');
            galleryContainer.classList.add('gallery');

            uploadImagesButton.addEventListener('change', function (event) {
                const files = event.target.files;
                galleryContainer.innerHTML = '';

                Array.from(files).forEach(file => {
                    const img = document.createElement('img');
                    const reader = new FileReader();

                    reader.onload = function (e) {
                        img.src = e.target.result;
                        img.onclick = function () {
                            showLightbox(e.target.result);
                        };
                        galleryContainer.appendChild(img);
                    };
                    reader.readAsDataURL(file);
                });
            });

            userJourneySection.appendChild(userJourneyLabel);
            userJourneySection.appendChild(uploadImagesButton);
            userJourneySection.appendChild(galleryContainer);

            // Status Labels: Last Executed On & Automation Status
            const statusSection = document.createElement('div');
            statusSection.classList.add('status-labels');

            const lastExecutedOnLabel = document.createElement('label');
            lastExecutedOnLabel.textContent = 'Last Executed On: Not executed yet';

            const automationStatusLabel = document.createElement('label');
            automationStatusLabel.textContent = 'Automation Status: Pending';

            statusSection.appendChild(lastExecutedOnLabel);
            statusSection.appendChild(automationStatusLabel);

            // Execute and Update Buttons
            const buttonSection = document.createElement('div');
            buttonSection.classList.add('subsection-buttons');

            const executeButton = document.createElement('button');
            executeButton.textContent = 'Execute';
            executeButton.onclick = function () {
                alert(`Executing: ${newTestCaseTitle}`);
            };

            const updateButton = document.createElement('button');
            updateButton.textContent = 'Update';
            updateButton.onclick = function () {
                alert(`Updating: ${newTestCaseTitle}`);
            };

            buttonSection.appendChild(executeButton);
            buttonSection.appendChild(updateButton);

            details.appendChild(summary);
            details.appendChild(preconditionSection);
            details.appendChild(testStepsSection);
            details.appendChild(actualResultSection);
            details.appendChild(expectedResultSection);
            details.appendChild(userJourneySection);
            details.appendChild(statusSection);
            details.appendChild(buttonSection);
            container.appendChild(details);
        }

        // Event listener for 'Add New Test Case' button
        document.getElementById('addNewTestCaseBtn').addEventListener('click', addNewTestCase);

        // Fetch API data on page load
        fetchData();
    </script>
</body>
</html>
