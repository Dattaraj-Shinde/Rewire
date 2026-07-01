package com.rewire.app.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.navigation.NavController
import androidx.compose.foundation.layout.PaddingValues

@Composable
fun DashboardScreen(
    navController: NavController,
    innerPadding: PaddingValues
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF14141E))
            .statusBarsPadding()
            .padding(innerPadding)
            .padding(
                start = 30.dp,
                end = 30.dp,
                top = 18.dp
            ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        item {

            GreetingManager()

        }

        item {

            ImprovementScoreCard()

        }

        item {

            DashboardStatsSection()

        }

        item {

            Spacer(
                modifier = Modifier.height(24.dp)
            )

        }

    }
}

@Composable
fun ImprovementScoreCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1B25)
        ),
        shape = RoundedCornerShape(28.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "IMPROVEMENT SCORE",
                color = Color(0xFF9EA5B8),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            Box(
                contentAlignment = Alignment.Center
            ) {

                Canvas(
                    modifier = Modifier.size(210.dp)
                ) {

                    drawArc(
                        color = Color(0xFF2A2C39),
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(
                            width = 18.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )

                    drawArc(
                        color = Color(0xFF19C4F4),
                        startAngle = 145f,
                        sweepAngle = 300f,
                        useCenter = false,
                        style = Stroke(
                            width = 18.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                }

                Text(
                    text = "84",
                    color = Color.White,
                    fontSize = 64.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            Surface(
                color = Color(0xFF17394A),
                shape = RoundedCornerShape(50.dp)
            ) {

                Row(
                    modifier = Modifier.padding(
                        horizontal = 22.dp,
                        vertical = 12.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.ArrowOutward,
                        contentDescription = null,
                        tint = Color(0xFF19C4F4),
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = "+12% this week",
                        color = Color(0xFF19C4F4),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }

        }

    }
}

@Composable
fun DashboardStatsSection() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        Box(
            modifier = Modifier.weight(1.05f)
        ) {
            RecoveryLoadCard()
        }

        Column(
            modifier = Modifier.weight(0.95f),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            TimeSavedCard()

            SessionsAvoidedCard()
        }
    }
}

@Composable
fun RecoveryLoadCard() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(285.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1B1C27)
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp, vertical = 22.dp)
        ) {

            Text(
                text = "Recovery & Load",
                color = Color(0xFFA8B0C2),
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentAlignment = Alignment.Center
            ) {

                Canvas(
                    modifier = Modifier.size(130.dp)
                ) {

                    val outerStroke = 13.dp.toPx()
                    val innerStroke = 13.dp.toPx()

                    drawArc(
                        color = Color(0xFF2C2D39),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(outerStroke, cap = StrokeCap.Round)
                    )

                    drawArc(
                        color = Color(0xFFA44CFF),
                        startAngle = 150f,
                        sweepAngle = 285f,
                        useCenter = false,
                        style = Stroke(outerStroke, cap = StrokeCap.Round)
                    )

                    val inset = 20.dp.toPx()

                    drawArc(
                        color = Color(0xFF2C2D39),
                        topLeft = Offset(inset, inset),
                        size = Size(
                            size.width - inset * 2,
                            size.height - inset * 2
                        ),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(innerStroke, cap = StrokeCap.Round)
                    )

                    drawArc(
                        color = Color(0xFF4E8DFF),
                        topLeft = Offset(inset, inset),
                        size = Size(
                            size.width - inset * 2,
                            size.height - inset * 2
                        ),
                        startAngle = 275f,
                        sweepAngle = 155f,
                        useCenter = false,
                        style = Stroke(innerStroke, cap = StrokeCap.Round)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(
                                Color(0xFFA44CFF),
                                CircleShape
                            )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        "76%",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(
                                Color(0xFF4E8DFF),
                                CircleShape
                            )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        "45%",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun TimeSavedCard() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(133.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1B1F29)
        )
    ) {

        Column(
            modifier = Modifier.padding(22.dp)
        ) {

            Text(
                "Time Saved",
                color = Color(0xFFA8B0C2),
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                "4h 20m",
                color = Color(0xFF1FD28B),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SessionsAvoidedCard() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(133.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1B1C27)
        )
    ) {

        Column(
            modifier = Modifier.padding(22.dp)
        ) {

            Text(
                "Sessions Avoided",
                color = Color(0xFFA8B0C2),
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                "38",
                color = Color(0xFFA44CFF),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
