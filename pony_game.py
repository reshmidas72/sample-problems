import pygame
import sys

# Initialize pygame
pygame.init()

# Screen setup
WIDTH, HEIGHT = 800, 600
screen = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("🏓 Pong Game")

# Colors
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)

# Paddle setup
PADDLE_WIDTH, PADDLE_HEIGHT = 10, 100
player = pygame.Rect(WIDTH - 20, HEIGHT // 2 - PADDLE_HEIGHT // 2, PADDLE_WIDTH, PADDLE_HEIGHT)
opponent = pygame.Rect(10, HEIGHT // 2 - PADDLE_HEIGHT // 2, PADDLE_WIDTH, PADDLE_HEIGHT)

# Ball setup
BALL_SIZE = 20
ball = pygame.Rect(WIDTH // 2 - BALL_SIZE // 2, HEIGHT // 2 - BALL_SIZE // 2, BALL_SIZE, BALL_SIZE)
ball_speed_x = 5
ball_speed_y = 5

# Scores
player_score = 0
opponent_score = 0
font = pygame.font.SysFont("Arial", 36)

# Game variables
paddle_speed = 7
opponent_speed = 7
clock = pygame.time.Clock()

def draw_objects():
    screen.fill(BLACK)
    pygame.draw.rect(screen, WHITE, player)
    pygame.draw.rect(screen, WHITE, opponent)
    pygame.draw.ellipse(screen, WHITE, ball)
    pygame.draw.aaline(screen, WHITE, (WIDTH // 2, 0), (WIDTH // 2, HEIGHT))

    score_text = font.render(f"{opponent_score}  -  {player_score}", True, WHITE)
    screen.blit(score_text, (WIDTH // 2 - 50, 20))

# Main game loop
running = True
while running:
    # Events
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    # Controls
    keys = pygame.key.get_pressed()
    if keys[pygame.K_UP] and player.top > 0:
        player.y -= paddle_speed
    if keys[pygame.K_DOWN] and player.bottom < HEIGHT:
        player.y += paddle_speed

    # Opponent AI
    if opponent.centery < ball.centery and opponent.bottom < HEIGHT:
        opponent.y += opponent_speed
    if opponent.centery > ball.centery and opponent.top > 0:
        opponent.y -= opponent_speed

    # Ball movement
    ball.x += ball_speed_x
    ball.y += ball_speed_y

    # Collision with top/bottom walls
    if ball.top <= 0 or ball.bottom >= HEIGHT:
        ball_speed_y *= -1

    # Collision with paddles
    if ball.colliderect(player) or ball.colliderect(opponent):
        ball_speed_x *= -1

    # Scoring
    if ball.left <= 0:
        player_score += 1
        ball.center = (WIDTH // 2, HEIGHT // 2)
        ball_speed_x *= -1
    if ball.right >= WIDTH:
        opponent_score += 1
        ball.center = (WIDTH // 2, HEIGHT // 2)
        ball_speed_x *= -1

    # Draw everything
    draw_objects()

    # Update display
    pygame.display.update()
    clock.tick(60)

pygame.quit()
sys.exit()
